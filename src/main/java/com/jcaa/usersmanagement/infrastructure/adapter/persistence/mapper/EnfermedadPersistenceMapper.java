package com.jcaa.usersmanagement.infrastructure.adapter.persistence.mapper;

import com.jcaa.usersmanagement.domain.enums.NivelGravedad;
import com.jcaa.usersmanagement.domain.model.EnfermedadModel;
import com.jcaa.usersmanagement.domain.valueobject.EnfermedadId;
import com.jcaa.usersmanagement.domain.valueobject.Medicamentos;
import com.jcaa.usersmanagement.domain.valueobject.NivelGravedadVO;
import com.jcaa.usersmanagement.domain.valueobject.NombreCientifico;
import com.jcaa.usersmanagement.domain.valueobject.NombreEnfermedad;
import com.jcaa.usersmanagement.domain.valueobject.Sintomas;
import com.jcaa.usersmanagement.infrastructure.adapter.persistence.dto.EnfermedadPersistenceDto;
import com.jcaa.usersmanagement.infrastructure.adapter.persistence.entity.EnfermedadEntity;
import lombok.experimental.UtilityClass;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class EnfermedadPersistenceMapper {

  public EnfermedadPersistenceDto fromModelToDto(final EnfermedadModel enfermedad) {
    return new EnfermedadPersistenceDto(
        enfermedad.getId() != null ? enfermedad.getId().value() : null,
        enfermedad.getNombre().value(),
        enfermedad.getNombreCientifico().value(),
        formatNivelGravedad(enfermedad.getNivelGravedad().value()),
        enfermedad.getSintomas().value(),
        enfermedad.getMedicamentos().value(),
        enfermedad.isEsContagiosa(),
        enfermedad.isEsCubiertaPorPos(),
        enfermedad.isRequiereIncapacidad());
  }

  public EnfermedadEntity fromResultSetToEntity(final ResultSet resultSet) throws SQLException {
    return new EnfermedadEntity(
        resultSet.getLong("id"),
        resultSet.getString("nombre"),
        resultSet.getString("nombreCientifico"),
        resultSet.getString("nivelGravedad"),
        resultSet.getString("sintomas"),
        resultSet.getString("medicamentos"),
        resultSet.getBoolean("esContagiosa"),
        resultSet.getBoolean("esCubiertaPorPos"),
        resultSet.getBoolean("requiereIncapacidad"));
  }

  public EnfermedadModel fromEntityToModel(final EnfermedadEntity entity) {
    return new EnfermedadModel(
        new EnfermedadId(entity.id()),
        new NombreEnfermedad(entity.nombre()),
        new NombreCientifico(entity.nombreCientifico()),
        NivelGravedadVO.fromString(entity.nivelGravedad()),
        new Sintomas(entity.sintomas()),
        new Medicamentos(entity.medicamentos()),
        Boolean.TRUE.equals(entity.esContagiosa()),
        Boolean.TRUE.equals(entity.esCubiertaPorPos()),
        Boolean.TRUE.equals(entity.requiereIncapacidad()));
  }

  public EnfermedadModel fromResultSetToModel(final ResultSet resultSet) throws SQLException {
    return fromEntityToModel(fromResultSetToEntity(resultSet));
  }

  public List<EnfermedadModel> fromResultSetToModelList(final ResultSet resultSet)
      throws SQLException {
    final List<EnfermedadModel> enfermedades = new ArrayList<>();
    while (resultSet.next()) {
      enfermedades.add(fromResultSetToModel(resultSet));
    }
    return enfermedades;
  }

  private String formatNivelGravedad(final NivelGravedad nivel) {
    final String name = nivel.name();
    return name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
  }
}
