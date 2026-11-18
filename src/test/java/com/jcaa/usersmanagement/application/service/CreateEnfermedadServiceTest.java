package com.jcaa.usersmanagement.application.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.jcaa.usersmanagement.application.port.out.GetEnfermedadByNombrePort;
import com.jcaa.usersmanagement.application.port.out.SaveEnfermedadPort;
import com.jcaa.usersmanagement.application.service.dto.command.CreateEnfermedadCommand;
import com.jcaa.usersmanagement.domain.enums.NivelGravedad;
import com.jcaa.usersmanagement.domain.exception.EnfermedadAlreadyExistsException;
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

@DisplayName("CreateEnfermedadService")
@ExtendWith(MockitoExtension.class)
class CreateEnfermedadServiceTest {

  @Mock private SaveEnfermedadPort saveEnfermedadPort;
  @Mock private GetEnfermedadByNombrePort getEnfermedadByNombrePort;

  private CreateEnfermedadService service;

  private static final String NOMBRE = "Gripe común";
  private static final String NOMBRE_CIENTIFICO = "Influenza";
  private static final String NIVEL = "Leve";
  private static final String SINTOMAS = "Fiebre, tos, dolor de cabeza";
  private static final String MEDICAMENTOS = "Paracetamol, Loratadina";

  @BeforeEach
  void setUp() {
    try (final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory()) {
      service =
          new CreateEnfermedadService(
              saveEnfermedadPort,
              getEnfermedadByNombrePort,
              validatorFactory.getValidator());
    }
  }

  @Test
  @DisplayName("execute() saves and returns enfermedad when nombre is new")
  void shouldSaveEnfermedadWhenNombreIsNew() {
    final CreateEnfermedadCommand command =
        new CreateEnfermedadCommand(
            NOMBRE, NOMBRE_CIENTIFICO, NIVEL, SINTOMAS, MEDICAMENTOS, true, true, false);

    when(getEnfermedadByNombrePort.getByNombre(any(NombreEnfermedad.class)))
        .thenReturn(Optional.empty());

    final EnfermedadModel savedModel =
        EnfermedadModel.create(
                new NombreEnfermedad(NOMBRE),
                new NombreCientifico(NOMBRE_CIENTIFICO),
                new NivelGravedadVO(NivelGravedad.LEVE),
                new Sintomas(SINTOMAS),
                new Medicamentos(MEDICAMENTOS),
                true,
                true,
                false)
            .withId(new EnfermedadId(1L));

    when(saveEnfermedadPort.save(any(EnfermedadModel.class))).thenReturn(savedModel);

    final EnfermedadModel result = service.execute(command);

    assertNotNull(result);
    assertEquals(1L, result.getId().value());
    assertEquals(NOMBRE, result.getNombre().value());
    verify(getEnfermedadByNombrePort).getByNombre(new NombreEnfermedad(NOMBRE));
    verify(saveEnfermedadPort).save(any(EnfermedadModel.class));
  }

  @Test
  @DisplayName("execute() throws EnfermedadAlreadyExistsException when nombre already exists")
  void shouldThrowWhenNombreAlreadyExists() {
    final CreateEnfermedadCommand command =
        new CreateEnfermedadCommand(
            NOMBRE, NOMBRE_CIENTIFICO, NIVEL, SINTOMAS, MEDICAMENTOS, true, true, false);

    final EnfermedadModel existing =
        EnfermedadModel.create(
                new NombreEnfermedad(NOMBRE),
                new NombreCientifico(NOMBRE_CIENTIFICO),
                new NivelGravedadVO(NivelGravedad.LEVE),
                new Sintomas(SINTOMAS),
                new Medicamentos(MEDICAMENTOS),
                true,
                true,
                false)
            .withId(new EnfermedadId(1L));

    when(getEnfermedadByNombrePort.getByNombre(any(NombreEnfermedad.class)))
        .thenReturn(Optional.of(existing));

    assertThrows(EnfermedadAlreadyExistsException.class, () -> service.execute(command));
    verify(saveEnfermedadPort, never()).save(any(EnfermedadModel.class));
  }

  @Test
  @DisplayName("execute() throws ConstraintViolationException when command has invalid fields")
  void shouldThrowWhenCommandHasInvalidFields() {
    final CreateEnfermedadCommand invalidCommand =
        new CreateEnfermedadCommand(
            "", NOMBRE_CIENTIFICO, NIVEL, SINTOMAS, MEDICAMENTOS, null, true, false);

    assertThrows(ConstraintViolationException.class, () -> service.execute(invalidCommand));
    verifyNoInteractions(getEnfermedadByNombrePort, saveEnfermedadPort);
  }
}
