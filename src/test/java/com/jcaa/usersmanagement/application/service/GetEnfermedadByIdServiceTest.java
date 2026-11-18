package com.jcaa.usersmanagement.application.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.jcaa.usersmanagement.application.port.out.GetEnfermedadByIdPort;
import com.jcaa.usersmanagement.application.service.dto.query.GetEnfermedadByIdQuery;
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

@DisplayName("GetEnfermedadByIdService")
@ExtendWith(MockitoExtension.class)
class GetEnfermedadByIdServiceTest {

  @Mock private GetEnfermedadByIdPort getEnfermedadByIdPort;

  private GetEnfermedadByIdService service;

  private static final Long ID = 1L;

  @BeforeEach
  void setUp() {
    try (final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory()) {
      service =
          new GetEnfermedadByIdService(
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
  @DisplayName("execute() returns EnfermedadModel when found")
  void shouldReturnEnfermedadWhenFound() {
    final GetEnfermedadByIdQuery query = new GetEnfermedadByIdQuery(ID);
    when(getEnfermedadByIdPort.getById(new EnfermedadId(ID)))
        .thenReturn(Optional.of(buildModel(ID)));

    final EnfermedadModel result = service.execute(query);

    assertNotNull(result);
    assertEquals(ID, result.getId().value());
    verify(getEnfermedadByIdPort).getById(new EnfermedadId(ID));
  }

  @Test
  @DisplayName("execute() throws EnfermedadNotFoundException when id is not found")
  void shouldThrowWhenIdNotFound() {
    final GetEnfermedadByIdQuery query = new GetEnfermedadByIdQuery(ID);
    when(getEnfermedadByIdPort.getById(new EnfermedadId(ID))).thenReturn(Optional.empty());

    assertThrows(EnfermedadNotFoundException.class, () -> service.execute(query));
  }

  @Test
  @DisplayName("execute() throws ConstraintViolationException when query id is invalid")
  void shouldThrowWhenQueryIsInvalid() {
    final GetEnfermedadByIdQuery query = new GetEnfermedadByIdQuery(-1L);

    assertThrows(ConstraintViolationException.class, () -> service.execute(query));
    verifyNoInteractions(getEnfermedadByIdPort);
  }
}
