package com.jcaa.usersmanagement.application.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.jcaa.usersmanagement.application.port.out.DeleteEnfermedadPort;
import com.jcaa.usersmanagement.application.port.out.GetEnfermedadByIdPort;
import com.jcaa.usersmanagement.application.service.dto.command.DeleteEnfermedadCommand;
import com.jcaa.usersmanagement.domain.enums.NivelGravedad;
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

@DisplayName("DeleteEnfermedadService")
@ExtendWith(MockitoExtension.class)
class DeleteEnfermedadServiceTest {

  @Mock private DeleteEnfermedadPort deleteEnfermedadPort;
  @Mock private GetEnfermedadByIdPort getEnfermedadByIdPort;

  private DeleteEnfermedadService service;

  private static final Long ID = 1L;

  @BeforeEach
  void setUp() {
    try (final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory()) {
      service =
          new DeleteEnfermedadService(
              deleteEnfermedadPort,
              getEnfermedadByIdPort,
              validatorFactory.getValidator());
    }
  }

  private EnfermedadModel buildModel(final Long id) {
    return EnfermedadModel.create(
            new NombreEnfermedad("Gripe"),
            new NombreCientifico("Influenza"),
            new NivelGravedadVO(NivelGravedad.LEVE),
            new Sintomas("Fiebre"),
            new Medicamentos("Paracetamol"),
            true,
            true,
            false)
        .withId(new EnfermedadId(id));
  }

  @Test
  @DisplayName("execute() deletes enfermedad when it exists")
  void shouldDeleteEnfermedadWhenExists() {
    final DeleteEnfermedadCommand command = new DeleteEnfermedadCommand(ID);
    when(getEnfermedadByIdPort.getById(new EnfermedadId(ID)))
        .thenReturn(Optional.of(buildModel(ID)));

    service.execute(command);

    verify(deleteEnfermedadPort).delete(new EnfermedadId(ID));
  }

  @Test
  @DisplayName("execute() throws EnfermedadNotFoundException when id does not exist")
  void shouldThrowWhenIdNotFound() {
    final DeleteEnfermedadCommand command = new DeleteEnfermedadCommand(ID);
    when(getEnfermedadByIdPort.getById(new EnfermedadId(ID))).thenReturn(Optional.empty());

    assertThrows(EnfermedadNotFoundException.class, () -> service.execute(command));
    verify(deleteEnfermedadPort, never()).delete(any(EnfermedadId.class));
  }

  @Test
  @DisplayName("execute() throws ConstraintViolationException when command id is invalid")
  void shouldThrowWhenCommandIsInvalid() {
    final DeleteEnfermedadCommand command = new DeleteEnfermedadCommand(-1L);

    assertThrows(ConstraintViolationException.class, () -> service.execute(command));
    verifyNoInteractions(getEnfermedadByIdPort, deleteEnfermedadPort);
  }
}
