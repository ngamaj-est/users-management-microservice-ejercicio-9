package com.jcaa.usersmanagement.infrastructure.adapter.persistence.repository;

import com.jcaa.usersmanagement.application.port.out.DeleteEnfermedadPort;
import com.jcaa.usersmanagement.application.port.out.GetAllEnfermedadesPort;
import com.jcaa.usersmanagement.application.port.out.GetEnfermedadByIdPort;
import com.jcaa.usersmanagement.application.port.out.GetEnfermedadByNombrePort;
import com.jcaa.usersmanagement.application.port.out.SaveEnfermedadPort;
import com.jcaa.usersmanagement.application.port.out.UpdateEnfermedadPort;
import com.jcaa.usersmanagement.domain.exception.EnfermedadNotFoundException;
import com.jcaa.usersmanagement.domain.model.EnfermedadModel;
import com.jcaa.usersmanagement.domain.valueobject.EnfermedadId;
import com.jcaa.usersmanagement.domain.valueobject.NombreEnfermedad;
import com.jcaa.usersmanagement.infrastructure.adapter.persistence.dto.EnfermedadPersistenceDto;
import com.jcaa.usersmanagement.infrastructure.adapter.persistence.exception.PersistenceException;
import com.jcaa.usersmanagement.infrastructure.adapter.persistence.mapper.EnfermedadPersistenceMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class EnfermedadRepositoryMySQL
    implements SaveEnfermedadPort,
        UpdateEnfermedadPort,
        GetEnfermedadByIdPort,
        GetEnfermedadByNombrePort,
        GetAllEnfermedadesPort,
        DeleteEnfermedadPort {

  private static final String SQL_INSERT =
      "INSERT INTO Enfermedades "
          + "(nombre, nombreCientifico, nivelGravedad, sintomas, medicamentos, esContagiosa, esCubiertaPorPos, requiereIncapacidad) "
          + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

  private static final String SQL_UPDATE =
      "UPDATE Enfermedades SET "
          + "nombre = ?, nombreCientifico = ?, nivelGravedad = ?, sintomas = ?, medicamentos = ?, "
          + "esContagiosa = ?, esCubiertaPorPos = ?, requiereIncapacidad = ? "
          + "WHERE id = ?";

  private static final String SQL_SELECT_BY_ID =
      "SELECT id, nombre, nombreCientifico, nivelGravedad, sintomas, medicamentos, esContagiosa, esCubiertaPorPos, requiereIncapacidad "
          + "FROM Enfermedades "
          + "WHERE id = ? LIMIT 1";

  private static final String SQL_SELECT_BY_NOMBRE =
      "SELECT id, nombre, nombreCientifico, nivelGravedad, sintomas, medicamentos, esContagiosa, esCubiertaPorPos, requiereIncapacidad "
          + "FROM Enfermedades "
          + "WHERE nombre = ? LIMIT 1";

  private static final String SQL_SELECT_ALL =
      "SELECT id, nombre, nombreCientifico, nivelGravedad, sintomas, medicamentos, esContagiosa, esCubiertaPorPos, requiereIncapacidad "
          + "FROM Enfermedades "
          + "ORDER BY nombre ASC";

  private static final String SQL_DELETE =
      "DELETE FROM Enfermedades WHERE id = ?";

  private final DataSource dataSource;

  @Override
  public EnfermedadModel save(final EnfermedadModel enfermedad) {
    final EnfermedadPersistenceDto dto = EnfermedadPersistenceMapper.fromModelToDto(enfermedad);
    final long generatedId = executeSave(dto);
    return findByIdOrFail(new EnfermedadId(generatedId));
  }

  @Override
  public EnfermedadModel update(final EnfermedadModel enfermedad) {
    final EnfermedadPersistenceDto dto = EnfermedadPersistenceMapper.fromModelToDto(enfermedad);
    executeUpdate(dto);
    return findByIdOrFail(enfermedad.getId());
  }

  @Override
  public Optional<EnfermedadModel> getById(final EnfermedadId id) {
    try (final Connection connection = dataSource.getConnection();
         final PreparedStatement statement = connection.prepareStatement(SQL_SELECT_BY_ID)) {
      statement.setLong(1, id.value());
      final ResultSet resultSet = statement.executeQuery();
      if (!resultSet.next()) {
        return Optional.empty();
      }
      return Optional.of(EnfermedadPersistenceMapper.fromResultSetToModel(resultSet));
    } catch (final SQLException exception) {
      throw PersistenceException.becauseFindEnfermedadByIdFailed(id.value(), exception);
    }
  }

  @Override
  public Optional<EnfermedadModel> getByNombre(final NombreEnfermedad nombre) {
    try (final Connection connection = dataSource.getConnection();
         final PreparedStatement statement = connection.prepareStatement(SQL_SELECT_BY_NOMBRE)) {
      statement.setString(1, nombre.value());
      final ResultSet resultSet = statement.executeQuery();
      if (!resultSet.next()) {
        return Optional.empty();
      }
      return Optional.of(EnfermedadPersistenceMapper.fromResultSetToModel(resultSet));
    } catch (final SQLException exception) {
      throw PersistenceException.becauseFindEnfermedadByNombreFailed(nombre.value(), exception);
    }
  }

  @Override
  public List<EnfermedadModel> getAll() {
    try (final Connection connection = dataSource.getConnection();
         final PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ALL)) {
      final ResultSet resultSet = statement.executeQuery();
      return EnfermedadPersistenceMapper.fromResultSetToModelList(resultSet);
    } catch (final SQLException exception) {
      throw PersistenceException.becauseFindAllEnfermedadesFailed(exception);
    }
  }

  @Override
  public void delete(final EnfermedadId id) {
    try (final Connection connection = dataSource.getConnection();
         final PreparedStatement statement = connection.prepareStatement(SQL_DELETE)) {
      statement.setLong(1, id.value());
      statement.executeUpdate();
    } catch (final SQLException exception) {
      throw PersistenceException.becauseDeleteEnfermedadFailed(id.value(), exception);
    }
  }

  private long executeSave(final EnfermedadPersistenceDto dto) {
    try (final Connection connection = dataSource.getConnection();
         final PreparedStatement statement =
             connection.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS)) {
      statement.setString(1, dto.nombre());
      statement.setString(2, dto.nombreCientifico());
      statement.setString(3, dto.nivelGravedad());
      statement.setString(4, dto.sintomas());
      statement.setString(5, dto.medicamentos());
      statement.setBoolean(6, dto.esContagiosa());
      statement.setBoolean(7, dto.esCubiertaPorPos());
      statement.setBoolean(8, dto.requiereIncapacidad());
      statement.executeUpdate();

      try (final ResultSet generatedKeys = statement.getGeneratedKeys()) {
        if (generatedKeys.next()) {
          return generatedKeys.getLong(1);
        }
        throw PersistenceException.becauseSaveEnfermedadFailed(
            dto.nombre(), new SQLException("No generated ID obtained after saving enfermedad."));
      }
    } catch (final SQLException exception) {
      throw PersistenceException.becauseSaveEnfermedadFailed(dto.nombre(), exception);
    }
  }

  private void executeUpdate(final EnfermedadPersistenceDto dto) {
    try (final Connection connection = dataSource.getConnection();
         final PreparedStatement statement = connection.prepareStatement(SQL_UPDATE)) {
      statement.setString(1, dto.nombre());
      statement.setString(2, dto.nombreCientifico());
      statement.setString(3, dto.nivelGravedad());
      statement.setString(4, dto.sintomas());
      statement.setString(5, dto.medicamentos());
      statement.setBoolean(6, dto.esContagiosa());
      statement.setBoolean(7, dto.esCubiertaPorPos());
      statement.setBoolean(8, dto.requiereIncapacidad());
      statement.setLong(9, dto.id());
      statement.executeUpdate();
    } catch (final SQLException exception) {
      throw PersistenceException.becauseUpdateEnfermedadFailed(dto.id(), exception);
    }
  }

  private EnfermedadModel findByIdOrFail(final EnfermedadId id) {
    return getById(id)
        .orElseThrow(() -> EnfermedadNotFoundException.becauseIdWasNotFound(id.value()));
  }
}
