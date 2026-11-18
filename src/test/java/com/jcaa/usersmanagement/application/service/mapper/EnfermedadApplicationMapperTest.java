package com.jcaa.usersmanagement.application.service.mapper;

import static org.junit.jupiter.api.Assertions.*;

import com.jcaa.usersmanagement.application.service.dto.command.CreateEnfermedadCommand;
import com.jcaa.usersmanagement.application.service.dto.command.DeleteEnfermedadCommand;
import com.jcaa.usersmanagement.application.service.dto.command.UpdateEnfermedadCommand;
import com.jcaa.usersmanagement.application.service.dto.query.GetEnfermedadByIdQuery;
import com.jcaa.usersmanagement.application.service.dto.query.GetEnfermedadByNombreQuery;
import com.jcaa.usersmanagement.domain.enums.NivelGravedad;
import com.jcaa.usersmanagement.domain.model.EnfermedadModel;
import com.jcaa.usersmanagement.domain.valueobject.EnfermedadId;
import com.jcaa.usersmanagement.domain.valueobject.NombreEnfermedad;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("EnfermedadApplicationMapper")
class EnfermedadApplicationMapperTest {

  private static final Long ID = 1L;
  private static final String NOMBRE = "Gripe común";
  private static final String NOMBRE_CIENTIFICO = "Influenza";
  private static final String NIVEL_GRAVEDAD = "Leve";
  private static final String SINTOMAS = "Fiebre, tos, congestión nasal";
  private static final String MEDICAMENTOS = "Paracetamol, Loratadina";

  @Test
  @DisplayName("fromCreateCommandToModel() maps all fields and leaves id as null")
  void shouldMapCreateCommandToModel() {
    final CreateEnfermedadCommand command =
        new CreateEnfermedadCommand(
            NOMBRE, NOMBRE_CIENTIFICO, NIVEL_GRAVEDAD, SINTOMAS, MEDICAMENTOS, true, true, false);

    final EnfermedadModel result = EnfermedadApplicationMapper.fromCreateCommandToModel(command);

    assertAll(
        "fromCreateCommandToModel()",
        () -> assertNull(result.getId()),
        () -> assertEquals(NOMBRE, result.getNombre().value()),
        () -> assertEquals(NOMBRE_CIENTIFICO, result.getNombreCientifico().value()),
        () -> assertEquals(NivelGravedad.LEVE, result.getNivelGravedadEnum()),
        () -> assertEquals(SINTOMAS, result.getSintomas().value()),
        () -> assertEquals(MEDICAMENTOS, result.getMedicamentos().value()),
        () -> assertTrue(result.isEsContagiosa()),
        () -> assertTrue(result.isEsCubiertaPorPos()),
        () -> assertFalse(result.isRequiereIncapacidad()));
  }

  @Test
  @DisplayName("fromUpdateCommandToModel() maps all fields including id")
  void shouldMapUpdateCommandToModel() {
    final UpdateEnfermedadCommand command =
        new UpdateEnfermedadCommand(
            ID, NOMBRE, NOMBRE_CIENTIFICO, NIVEL_GRAVEDAD, SINTOMAS, MEDICAMENTOS, false, true, true);

    final EnfermedadModel result = EnfermedadApplicationMapper.fromUpdateCommandToModel(command);

    assertAll(
        "fromUpdateCommandToModel()",
        () -> assertEquals(ID, result.getId().value()),
        () -> assertEquals(NOMBRE, result.getNombre().value()),
        () -> assertEquals(NOMBRE_CIENTIFICO, result.getNombreCientifico().value()),
        () -> assertEquals(NivelGravedad.LEVE, result.getNivelGravedadEnum()),
        () -> assertEquals(SINTOMAS, result.getSintomas().value()),
        () -> assertEquals(MEDICAMENTOS, result.getMedicamentos().value()),
        () -> assertFalse(result.isEsContagiosa()),
        () -> assertTrue(result.isEsCubiertaPorPos()),
        () -> assertTrue(result.isRequiereIncapacidad()));
  }

  @Test
  @DisplayName("fromDeleteCommandToEnfermedadId() maps command id to EnfermedadId VO")
  void shouldMapDeleteCommandToEnfermedadId() {
    final DeleteEnfermedadCommand command = new DeleteEnfermedadCommand(ID);

    final EnfermedadId result = EnfermedadApplicationMapper.fromDeleteCommandToEnfermedadId(command);

    assertEquals(ID, result.value());
  }

  @Test
  @DisplayName("fromQueryToEnfermedadId() maps query id to EnfermedadId VO")
  void shouldMapGetByIdQueryToEnfermedadId() {
    final GetEnfermedadByIdQuery query = new GetEnfermedadByIdQuery(ID);

    final EnfermedadId result = EnfermedadApplicationMapper.fromQueryToEnfermedadId(query);

    assertEquals(ID, result.value());
  }

  @Test
  @DisplayName("fromQueryToNombreEnfermedad() maps query nombre to NombreEnfermedad VO")
  void shouldMapGetByNombreQueryToNombreEnfermedad() {
    final GetEnfermedadByNombreQuery query = new GetEnfermedadByNombreQuery(NOMBRE);

    final NombreEnfermedad result = EnfermedadApplicationMapper.fromQueryToNombreEnfermedad(query);

    assertEquals(NOMBRE, result.value());
  }
}
