package com.jcaa.usersmanagement.domain.model;

import static org.junit.jupiter.api.Assertions.*;

import com.jcaa.usersmanagement.domain.enums.NivelGravedad;
import com.jcaa.usersmanagement.domain.valueobject.EnfermedadId;
import com.jcaa.usersmanagement.domain.valueobject.Medicamentos;
import com.jcaa.usersmanagement.domain.valueobject.NivelGravedadVO;
import com.jcaa.usersmanagement.domain.valueobject.NombreCientifico;
import com.jcaa.usersmanagement.domain.valueobject.NombreEnfermedad;
import com.jcaa.usersmanagement.domain.valueobject.Sintomas;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Tests para EnfermedadModel.
 *
 * <p>Se testean los métodos con lógica real: {@code create()} que produce un modelo sin id,
 * {@code withId()} que asigna el id generado por la BD, {@code update()} que actualiza campos
 * manteniendo el id, y {@code getNivelGravedadEnum()} como accessor de conveniencia.
 */
@DisplayName("EnfermedadModel")
class EnfermedadModelTest {

  // ── Arrange (fixtures compartidas) ───────────────────────────────────────

  private NombreEnfermedad nombre;
  private NombreCientifico nombreCientifico;
  private NivelGravedadVO nivelGravedad;
  private Sintomas sintomas;
  private Medicamentos medicamentos;

  @BeforeEach
  void setUp() {
    nombre = new NombreEnfermedad("Gripe común");
    nombreCientifico = new NombreCientifico("Influenza");
    nivelGravedad = new NivelGravedadVO(NivelGravedad.LEVE);
    sintomas = new Sintomas("Fiebre, tos, congestión nasal");
    medicamentos = new Medicamentos("Paracetamol, Loratadina");
  }

  // ── create() ─────────────────────────────────────────────────────────────

  @Test
  @DisplayName("create() debe producir un modelo con id null y todos los campos proporcionados")
  void shouldCreateModelWithNullIdAndAllFields() {
    // Act
    final EnfermedadModel model =
        EnfermedadModel.create(
            nombre, nombreCientifico, nivelGravedad, sintomas, medicamentos, true, true, false);

    // Assert
    assertAll(
        "create() factory",
        () -> assertNull(model.getId(), "id debe ser null al crear sin BD"),
        () -> assertSame(nombre, model.getNombre(), "nombre debe preservarse"),
        () -> assertSame(nombreCientifico, model.getNombreCientifico(), "nombreCientifico debe preservarse"),
        () -> assertSame(nivelGravedad, model.getNivelGravedad(), "nivelGravedad debe preservarse"),
        () -> assertSame(sintomas, model.getSintomas(), "sintomas debe preservarse"),
        () -> assertSame(medicamentos, model.getMedicamentos(), "medicamentos debe preservarse"),
        () -> assertTrue(model.isEsContagiosa(), "esContagiosa debe ser true"),
        () -> assertTrue(model.isEsCubiertaPorPos(), "esCubiertaPorPos debe ser true"),
        () -> assertFalse(model.isRequiereIncapacidad(), "requiereIncapacidad debe ser false"));
  }

  // ── withId() ─────────────────────────────────────────────────────────────

  @Test
  @DisplayName("withId() debe retornar nueva instancia con el id asignado y demás campos intactos")
  void shouldAssignIdAndPreserveAllOtherFields() {
    // Arrange
    final EnfermedadModel created =
        EnfermedadModel.create(
            nombre, nombreCientifico, nivelGravedad, sintomas, medicamentos, false, true, true);
    final EnfermedadId assignedId = new EnfermedadId(42L);

    // Act
    final EnfermedadModel withId = created.withId(assignedId);

    // Assert
    assertAll(
        "resultado de withId()",
        () -> assertNotSame(created, withId, "debe ser una nueva instancia"),
        () -> assertSame(assignedId, withId.getId(), "id debe ser el asignado"),
        () -> assertNull(created.getId(), "el modelo original no debe mutarse"),
        () -> assertSame(nombre, withId.getNombre(), "nombre debe preservarse"),
        () -> assertSame(nombreCientifico, withId.getNombreCientifico(), "nombreCientifico debe preservarse"),
        () -> assertSame(nivelGravedad, withId.getNivelGravedad(), "nivelGravedad debe preservarse"),
        () -> assertSame(sintomas, withId.getSintomas(), "sintomas debe preservarse"),
        () -> assertSame(medicamentos, withId.getMedicamentos(), "medicamentos debe preservarse"),
        () -> assertFalse(withId.isEsContagiosa(), "esContagiosa debe preservarse"),
        () -> assertTrue(withId.isEsCubiertaPorPos(), "esCubiertaPorPos debe preservarse"),
        () -> assertTrue(withId.isRequiereIncapacidad(), "requiereIncapacidad debe preservarse"));
  }

  // ── update() ─────────────────────────────────────────────────────────────

  @Test
  @DisplayName("update() debe retornar nueva instancia con campos actualizados manteniendo el id")
  void shouldUpdateFieldsAndPreserveId() {
    // Arrange
    final EnfermedadId id = new EnfermedadId(1L);
    final EnfermedadModel original =
        new EnfermedadModel(id, nombre, nombreCientifico, nivelGravedad, sintomas, medicamentos,
            true, true, false);

    final NombreEnfermedad nuevoNombre = new NombreEnfermedad("COVID-19");
    final NombreCientifico nuevoNombreCientifico = new NombreCientifico("SARS-CoV-2");
    final NivelGravedadVO nuevoNivel = new NivelGravedadVO(NivelGravedad.GRAVE);
    final Sintomas nuevosSintomas = new Sintomas("Fiebre, tos seca, dificultad respiratoria");
    final Medicamentos nuevosMedicamentos = new Medicamentos("Paracetamol, Oxigeno suplementario");

    // Act
    final EnfermedadModel updated =
        original.update(
            nuevoNombre, nuevoNombreCientifico, nuevoNivel, nuevosSintomas, nuevosMedicamentos,
            true, true, true);

    // Assert
    assertAll(
        "resultado de update()",
        () -> assertNotSame(original, updated, "debe ser una nueva instancia"),
        () -> assertSame(id, updated.getId(), "id debe preservarse"),
        () -> assertSame(nuevoNombre, updated.getNombre(), "nombre debe actualizarse"),
        () -> assertSame(nuevoNombreCientifico, updated.getNombreCientifico(), "nombreCientifico debe actualizarse"),
        () -> assertSame(nuevoNivel, updated.getNivelGravedad(), "nivelGravedad debe actualizarse"),
        () -> assertSame(nuevosSintomas, updated.getSintomas(), "sintomas debe actualizarse"),
        () -> assertSame(nuevosMedicamentos, updated.getMedicamentos(), "medicamentos debe actualizarse"),
        () -> assertTrue(updated.isRequiereIncapacidad(), "requiereIncapacidad debe actualizarse a true"));
  }

  // ── getNivelGravedadEnum() ────────────────────────────────────────────────

  @Test
  @DisplayName("getNivelGravedadEnum() debe devolver el enum envuelto por NivelGravedadVO")
  void shouldReturnWrappedNivelGravedadEnum() {
    // Arrange
    final EnfermedadModel model =
        EnfermedadModel.create(
            nombre, nombreCientifico,
            new NivelGravedadVO(NivelGravedad.CRITICA),
            sintomas, medicamentos, false, false, false);

    // Act & Assert
    assertEquals(NivelGravedad.CRITICA, model.getNivelGravedadEnum());
  }
}
