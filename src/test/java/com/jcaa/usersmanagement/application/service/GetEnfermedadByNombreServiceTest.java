package com.jcaa.usersmanagement.application.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.jcaa.usersmanagement.application.port.out.GetEnfermedadByNombrePort;
import com.jcaa.usersmanagement.application.service.dto.query.GetEnfermedadByNombreQuery;
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

@DisplayName("GetEnfermedadByNombreService")
@ExtendWith(MockitoExtension.class)
class GetEnfermedadByNombreServiceTest {

  @Mock private GetEnfermedadByNombrePort getEnfermedadByNombrePort;

  private GetEnfermedadByNombreService service;

  private static final String NOMBRE = "Gripe común";

  @BeforeEach
  void setUp() {
    try (final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory()) {
      service =
          new GetEnfermedadByNombreService(
              getEnfermedadByNombrePort,
              validatorFactory.getValidator());
    }
  }

  private EnfermedadModel buildModel(final Long id, final String nombre) {
    return EnfermedadModel.create(
            new NombreEnfermedad(nombre),
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
  @DisplayName("execute() returns EnfermedadModel when found by nombre")
  void shouldReturnEnfermedadWhenFound() {
    final GetEnfermedadByNombreQuery query = new GetEnfermedadByNombreQuery(NOMBRE);
    when(getEnfermedadByNombrePort.getByNombre(new NombreEnfermedad(NOMBRE)))
        .thenReturn(Optional.of(buildModel(1L, NOMBRE)));

    final EnfermedadModel result = service.execute(query);

    assertNotNull(result);
    assertEquals(NOMBRE, result.getNombre().value());
    verify(getEnfermedadByNombrePort).getByNombre(new NombreEnfermedad(NOMBRE));
  }

  @Test
  @DisplayName("execute() throws EnfermedadNotFoundException when nombre is not found")
  void shouldThrowWhenNombreNotFound() {
    final GetEnfermedadByNombreQuery query = new GetEnfermedadByNombreQuery(NOMBRE);
    when(getEnfermedadByNombrePort.getByNombre(new NombreEnfermedad(NOMBRE)))
        .thenReturn(Optional.empty());

    assertThrows(EnfermedadNotFoundException.class, () -> service.execute(query));
  }

  @Test
  @DisplayName("execute() throws ConstraintViolationException when query nombre is invalid")
  void shouldThrowWhenQueryIsInvalid() {
    final GetEnfermedadByNombreQuery query = new GetEnfermedadByNombreQuery("");

    assertThrows(ConstraintViolationException.class, () -> service.execute(query));
    verifyNoInteractions(getEnfermedadByNombrePort);
  }
}
