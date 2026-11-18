package com.jcaa.usersmanagement.application.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.jcaa.usersmanagement.application.port.out.GetAllEnfermedadesPort;
import com.jcaa.usersmanagement.domain.enums.NivelGravedad;
import com.jcaa.usersmanagement.domain.model.EnfermedadModel;
import com.jcaa.usersmanagement.domain.valueobject.EnfermedadId;
import com.jcaa.usersmanagement.domain.valueobject.Medicamentos;
import com.jcaa.usersmanagement.domain.valueobject.NivelGravedadVO;
import com.jcaa.usersmanagement.domain.valueobject.NombreCientifico;
import com.jcaa.usersmanagement.domain.valueobject.NombreEnfermedad;
import com.jcaa.usersmanagement.domain.valueobject.Sintomas;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("GetAllEnfermedadesService")
@ExtendWith(MockitoExtension.class)
class GetAllEnfermedadesServiceTest {

  @Mock private GetAllEnfermedadesPort getAllEnfermedadesPort;

  private GetAllEnfermedadesService service;

  @BeforeEach
  void setUp() {
    service = new GetAllEnfermedadesService(getAllEnfermedadesPort);
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
  @DisplayName("execute() returns all enfermedades from port")
  void shouldReturnAllEnfermedades() {
    final List<EnfermedadModel> expected =
        List.of(buildModel(1L, "Gripe común"), buildModel(2L, "COVID-19"));
    when(getAllEnfermedadesPort.getAll()).thenReturn(expected);

    final List<EnfermedadModel> result = service.execute();

    assertEquals(2, result.size());
    assertEquals("Gripe común", result.get(0).getNombre().value());
    assertEquals("COVID-19", result.get(1).getNombre().value());
    verify(getAllEnfermedadesPort).getAll();
  }

  @Test
  @DisplayName("execute() returns empty list when no enfermedades exist")
  void shouldReturnEmptyListWhenNoneExist() {
    when(getAllEnfermedadesPort.getAll()).thenReturn(Collections.emptyList());

    final List<EnfermedadModel> result = service.execute();

    assertTrue(result.isEmpty());
    verify(getAllEnfermedadesPort).getAll();
  }
}
