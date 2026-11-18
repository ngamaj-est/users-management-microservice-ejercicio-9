package com.jcaa.usersmanagement.application.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.jcaa.usersmanagement.application.port.out.GetEnfermedadByIdPort;
import com.jcaa.usersmanagement.application.port.out.GetEnfermedadByNombrePort;
import com.jcaa.usersmanagement.application.port.out.UpdateEnfermedadPort;
import com.jcaa.usersmanagement.application.service.dto.command.UpdateEnfermedadCommand;
import com.jcaa.usersmanagement.domain.enums.NivelGravedad;
import com.jcaa.usersmanagement.domain.exception.EnfermedadAlreadyExistsException;
import com.jcaa.usersmanagement.domain.exception.EnfermedadNotFoundException;
import com.jcaa.usersmanagement.domain.model.EnfermedadModel;
import com.jcaa.usersmanagement.domain.valueobject.EnfermedadId;
import com.jcaa.usersmanagement.domain.valueobject.Medicamentos;
import com.jcaa.usersmanagement.domain.valueobject.NivelGravedadVO;
import com.jcaa.usersmanagement.domain.valueobject.NombreCientifico;
import com.jcaa.usersmanagement.domain.valueobject.NombreEnfermedad;
import com.jcaa.usersmanagement.domain.valueobject.Sintomas;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validation;
import jakarta.validation.ValidatorFactory;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("UpdateEnfermedadService")
@ExtendWith(MockitoExtension.class)
class UpdateEnfermedadServiceTest {

  @Mock private UpdateEnfermedadPort updateEnfermedadPort;
  @Mock private GetEnfermedadByIdPort getEnfermedadByIdPort;
  @Mock private GetEnfermedadByNombrePort getEnfermedadByNombrePort;

  private UpdateEnfermedadService service;

  private static final Long ID = 1L;
  private static final String NOMBRE = "Gripe común";
  private static final String NOMBRE_CIENTIFICO = "Influenza";
  private static final String NIVEL = "Leve";
  private static final String SINTOMAS = "Fiebre, tos, dolor de cabeza";
  private static final String MEDICAMENTOS = "Paracetamol, Loratadina";

  @BeforeEach
  void setUp() {
    try (final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory()) {
      service =
          new UpdateEnfermedadService(
              updateEnfermedadPort,
              getEnfermedadByIdPort,
              getEnfermedadByNombrePort,
              validatorFactory.getValidator());
    }
  }

  private EnfermedadModel buildModel(final Long id, final String nombre) {
    return EnfermedadModel.create(
            new NombreEnfermedad(nombre),
            new NombreCientifico(NOMBRE_CIENTIFICO),
            new NivelGravedadVO(NivelGravedad.LEVE),
            new Sintomas(SINTOMAS),
            new Medicamentos(MEDICAMENTOS),
            true,
            true,
            false)
        .withId(new EnfermedadId(id));
  }

  @Test
  @DisplayName("execute() updates and returns enfermedad on happy path")
  void shouldUpdateEnfermedadSuccessfully() {
    final UpdateEnfermedadCommand command =
        new UpdateEnfermedadCommand(
            ID, NOMBRE, NOMBRE_CIENTIFICO, NIVEL, SINTOMAS, MEDICAMENTOS, true, true, false);

    when(getEnfermedadByIdPort.getById(new EnfermedadId(ID)))
        .thenReturn(Optional.of(buildModel(ID, NOMBRE)));
    when(getEnfermedadByNombrePort.getByNombre(new NombreEnfermedad(NOMBRE)))
        .thenReturn(Optional.empty());

    final EnfermedadModel updatedModel = buildModel(ID, NOMBRE);
    when(updateEnfermedadPort.update(any(EnfermedadModel.class))).thenReturn(updatedModel);

    final EnfermedadModel result = service.execute(command);

    assertNotNull(result);
    assertEquals(ID, result.getId().value());
    verify(updateEnfermedadPort).update(any(EnfermedadModel.class));
  }

  @Test
  @DisplayName("execute() throws EnfermedadNotFoundException when id does not exist")
  void shouldThrowWhenIdNotFound() {
    final UpdateEnfermedadCommand command =
        new UpdateEnfermedadCommand(
            ID, NOMBRE, NOMBRE_CIENTIFICO, NIVEL, SINTOMAS, MEDICAMENTOS, true, true, false);

    when(getEnfermedadByIdPort.getById(new EnfermedadId(ID))).thenReturn(Optional.empty());

    assertThrows(EnfermedadNotFoundException.class, () -> service.execute(command));
    verify(updateEnfermedadPort, never()).update(any(EnfermedadModel.class));
  }

  @Test
  @DisplayName("execute() throws EnfermedadAlreadyExistsException when nombre is taken by another record")
  void shouldThrowWhenNombreTakenByAnother() {
    final UpdateEnfermedadCommand command =
        new UpdateEnfermedadCommand(
            ID, NOMBRE, NOMBRE_CIENTIFICO, NIVEL, SINTOMAS, MEDICAMENTOS, true, true, false);

    when(getEnfermedadByIdPort.getById(new EnfermedadId(ID)))
        .thenReturn(Optional.of(buildModel(ID, "Old Name")));
    when(getEnfermedadByNombrePort.getByNombre(new NombreEnfermedad(NOMBRE)))
        .thenReturn(Optional.of(buildModel(2L, NOMBRE)));

    assertThrows(EnfermedadAlreadyExistsException.class, () -> service.execute(command));
    verify(updateEnfermedadPort, never()).update(any(EnfermedadModel.class));
  }

  @Test
  @DisplayName("execute() allows keeping the same nombre for the same id")
  void shouldAllowKeepingSameNombre() {
    final UpdateEnfermedadCommand command =
        new UpdateEnfermedadCommand(
            ID, NOMBRE, NOMBRE_CIENTIFICO, NIVEL, SINTOMAS, MEDICAMENTOS, true, true, false);

    when(getEnfermedadByIdPort.getById(new EnfermedadId(ID)))
        .thenReturn(Optional.of(buildModel(ID, NOMBRE)));
    when(getEnfermedadByNombrePort.getByNombre(new NombreEnfermedad(NOMBRE)))
        .thenReturn(Optional.of(buildModel(ID, NOMBRE)));

    final EnfermedadModel updatedModel = buildModel(ID, NOMBRE);
    when(updateEnfermedadPort.update(any(EnfermedadModel.class))).thenReturn(updatedModel);

    final EnfermedadModel result = service.execute(command);

    assertNotNull(result);
    verify(updateEnfermedadPort).update(any(EnfermedadModel.class));
  }

  @Test
  @DisplayName("execute() throws ConstraintViolationException when command is invalid")
  void shouldThrowWhenCommandIsInvalid() {
    final UpdateEnfermedadCommand command =
        new UpdateEnfermedadCommand(
            -1L, "", NOMBRE_CIENTIFICO, NIVEL, SINTOMAS, MEDICAMENTOS, true, true, false);

    assertThrows(ConstraintViolationException.class, () -> service.execute(command));
    verifyNoInteractions(getEnfermedadByIdPort, getEnfermedadByNombrePort, updateEnfermedadPort);
  }
}
