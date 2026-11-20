package com.jcaa.usersmanagement.infrastructure.adapter.persistence.mapper;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("EnfermedadPersistenceMapper")
@ExtendWith(MockitoExtension.class)
class EnfermedadPersistenceMapperTest {

  private static final Long ID = 1L;
  private static final String NOMBRE = "Gripe comun";
  private static final String NOMBRE_CIENTIFICO = "Influenza";
  private static final String NIVEL_GRAVEDAD_RAW = "Leve";
  private static final String SINTOMAS = "Fiebre, tos, dolor de cabeza";
  private static final String MEDICAMENTOS = "Paracetamol";
  private static final boolean ES_CONTAGIOSA = true;
  private static final boolean ES_CUBIERTA_POR_POS = true;
  private static final boolean REQUIERE_INCAPACIDAD = false;

  @Mock
  private ResultSet resultSet;

  private EnfermedadModel enfermedadModel;
  private EnfermedadEntity enfermedadEntity;

  @BeforeEach
  void setUp() {
    enfermedadModel = new EnfermedadModel(
        new EnfermedadId(ID),
        new NombreEnfermedad(NOMBRE),
        new NombreCientifico(NOMBRE_CIENTIFICO),
        new NivelGravedadVO(NivelGravedad.LEVE),
        new Sintomas(SINTOMAS),
        new Medicamentos(MEDICAMENTOS),
        ES_CONTAGIOSA,
        ES_CUBIERTA_POR_POS,
        REQUIERE_INCAPACIDAD);

    enfermedadEntity = new EnfermedadEntity(
        ID,
        NOMBRE,
        NOMBRE_CIENTIFICO,
        NIVEL_GRAVEDAD_RAW,
        SINTOMAS,
        MEDICAMENTOS,
        ES_CONTAGIOSA,
        ES_CUBIERTA_POR_POS,
        REQUIERE_INCAPACIDAD);
  }

  @Test
  @DisplayName("fromModelToDto() maps all fields and formats nivelGravedad to TitleCase")
  void shouldMapModelToDto() {
    final EnfermedadPersistenceDto result = EnfermedadPersistenceMapper.fromModelToDto(enfermedadModel);

    assertAll(
        "fromModelToDto()",
        () -> assertEquals(ID, result.id(), "id"),
        () -> assertEquals(NOMBRE, result.nombre(), "nombre"),
        () -> assertEquals(NOMBRE_CIENTIFICO, result.nombreCientifico(), "nombreCientifico"),
        () -> assertEquals("Leve", result.nivelGravedad(), "nivelGravedad must be TitleCase"),
        () -> assertEquals(SINTOMAS, result.sintomas(), "sintomas"),
        () -> assertEquals(MEDICAMENTOS, result.medicamentos(), "medicamentos"),
        () -> assertTrue(result.esContagiosa(), "esContagiosa"),
        () -> assertTrue(result.esCubiertaPorPos(), "esCubiertaPorPos"),
        () -> assertFalse(result.requiereIncapacidad(), "requiereIncapacidad"));
  }

  @Test
  @DisplayName("fromModelToDto() sets id to null when model has no id")
  void shouldHandleNullIdWhenMappingModelToDto() {
    final EnfermedadModel modelWithoutId = EnfermedadModel.create(
        new NombreEnfermedad(NOMBRE),
        new NombreCientifico(NOMBRE_CIENTIFICO),
        new NivelGravedadVO(NivelGravedad.LEVE),
        new Sintomas(SINTOMAS),
        new Medicamentos(MEDICAMENTOS),
        ES_CONTAGIOSA,
        ES_CUBIERTA_POR_POS,
        REQUIERE_INCAPACIDAD);

    final EnfermedadPersistenceDto result = EnfermedadPersistenceMapper.fromModelToDto(modelWithoutId);

    assertNull(result.id(), "id must be null for unsaved model");
  }

  @Test
  @DisplayName("fromEntityToModel() maps all entity fields to domain model")
  void shouldMapEntityToModel() {
    final EnfermedadModel result = EnfermedadPersistenceMapper.fromEntityToModel(enfermedadEntity);

    assertAll(
        "fromEntityToModel()",
        () -> assertEquals(ID, result.getId().value(), "id"),
        () -> assertEquals(NOMBRE, result.getNombre().value(), "nombre"),
        () -> assertEquals(NOMBRE_CIENTIFICO, result.getNombreCientifico().value(), "nombreCientifico"),
        () -> assertEquals(NivelGravedad.LEVE, result.getNivelGravedad().value(), "nivelGravedad"),
        () -> assertEquals(SINTOMAS, result.getSintomas().value(), "sintomas"),
        () -> assertEquals(MEDICAMENTOS, result.getMedicamentos().value(), "medicamentos"),
        () -> assertTrue(result.isEsContagiosa(), "esContagiosa"),
        () -> assertTrue(result.isEsCubiertaPorPos(), "esCubiertaPorPos"),
        () -> assertFalse(result.isRequiereIncapacidad(), "requiereIncapacidad"));
  }

  @Test
  @DisplayName("fromResultSetToEntity() reads all columns from ResultSet")
  void shouldReadAllColumnsFromResultSet() throws SQLException {
    when(resultSet.getLong("id")).thenReturn(ID);
    when(resultSet.getString("nombre")).thenReturn(NOMBRE);
    when(resultSet.getString("nombreCientifico")).thenReturn(NOMBRE_CIENTIFICO);
    when(resultSet.getString("nivelGravedad")).thenReturn(NIVEL_GRAVEDAD_RAW);
    when(resultSet.getString("sintomas")).thenReturn(SINTOMAS);
    when(resultSet.getString("medicamentos")).thenReturn(MEDICAMENTOS);
    when(resultSet.getBoolean("esContagiosa")).thenReturn(ES_CONTAGIOSA);
    when(resultSet.getBoolean("esCubiertaPorPos")).thenReturn(ES_CUBIERTA_POR_POS);
    when(resultSet.getBoolean("requiereIncapacidad")).thenReturn(REQUIERE_INCAPACIDAD);

    final EnfermedadEntity result = EnfermedadPersistenceMapper.fromResultSetToEntity(resultSet);

    assertAll(
        "fromResultSetToEntity()",
        () -> assertEquals(ID, result.id(), "id"),
        () -> assertEquals(NOMBRE, result.nombre(), "nombre"),
        () -> assertEquals(NOMBRE_CIENTIFICO, result.nombreCientifico(), "nombreCientifico"),
        () -> assertEquals(NIVEL_GRAVEDAD_RAW, result.nivelGravedad(), "nivelGravedad"),
        () -> assertEquals(SINTOMAS, result.sintomas(), "sintomas"),
        () -> assertEquals(MEDICAMENTOS, result.medicamentos(), "medicamentos"),
        () -> assertEquals(ES_CONTAGIOSA, result.esContagiosa(), "esContagiosa"),
        () -> assertEquals(ES_CUBIERTA_POR_POS, result.esCubiertaPorPos(), "esCubiertaPorPos"),
        () -> assertEquals(REQUIERE_INCAPACIDAD, result.requiereIncapacidad(), "requiereIncapacidad"));
  }

  @Test
  @DisplayName("fromResultSetToModelList() returns empty list when ResultSet is empty")
  void shouldReturnEmptyListWhenResultSetIsEmpty() throws SQLException {
    when(resultSet.next()).thenReturn(false);

    final List<EnfermedadModel> result = EnfermedadPersistenceMapper.fromResultSetToModelList(resultSet);

    assertTrue(result.isEmpty(), "must return empty list when no rows");
  }

  @Test
  @DisplayName("fromResultSetToModelList() returns mapped models for each row")
  void shouldReturnModelsListFromResultSet() throws SQLException {
    when(resultSet.next()).thenReturn(true, false);
    when(resultSet.getLong("id")).thenReturn(ID);
    when(resultSet.getString("nombre")).thenReturn(NOMBRE);
    when(resultSet.getString("nombreCientifico")).thenReturn(NOMBRE_CIENTIFICO);
    when(resultSet.getString("nivelGravedad")).thenReturn(NIVEL_GRAVEDAD_RAW);
    when(resultSet.getString("sintomas")).thenReturn(SINTOMAS);
    when(resultSet.getString("medicamentos")).thenReturn(MEDICAMENTOS);
    when(resultSet.getBoolean("esContagiosa")).thenReturn(ES_CONTAGIOSA);
    when(resultSet.getBoolean("esCubiertaPorPos")).thenReturn(ES_CUBIERTA_POR_POS);
    when(resultSet.getBoolean("requiereIncapacidad")).thenReturn(REQUIERE_INCAPACIDAD);

    final List<EnfermedadModel> result = EnfermedadPersistenceMapper.fromResultSetToModelList(resultSet);

    assertEquals(1, result.size());
    assertEquals(NOMBRE, result.get(0).getNombre().value());
  }
}