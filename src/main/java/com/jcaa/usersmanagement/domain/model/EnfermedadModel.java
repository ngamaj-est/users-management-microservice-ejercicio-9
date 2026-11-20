package com.jcaa.usersmanagement.domain.model;

import com.jcaa.usersmanagement.domain.enums.NivelGravedad;
import com.jcaa.usersmanagement.domain.valueobject.EnfermedadId;
import com.jcaa.usersmanagement.domain.valueobject.Medicamentos;
import com.jcaa.usersmanagement.domain.valueobject.NivelGravedadVO;
import com.jcaa.usersmanagement.domain.valueobject.NombreCientifico;
import com.jcaa.usersmanagement.domain.valueobject.NombreEnfermedad;
import com.jcaa.usersmanagement.domain.valueobject.Sintomas;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Value
@AllArgsConstructor
@Builder(toBuilder = true)
public class EnfermedadModel {

  EnfermedadId id;
  NombreEnfermedad nombre;
  NombreCientifico nombreCientifico;
  NivelGravedadVO nivelGravedad;
  Sintomas sintomas;
  Medicamentos medicamentos;
  boolean esContagiosa;
  boolean esCubiertaPorPos;
  boolean requiereIncapacidad;

  /**
   * Factory method para crear una nueva enfermedad sin id asignado aún (el id lo genera la BD
   * con SERIAL). Call {@link #withId(EnfermedadId)} once the persistence layer returns the key.
   */
  public static EnfermedadModel create(
      final NombreEnfermedad nombre,
      final NombreCientifico nombreCientifico,
      final NivelGravedadVO nivelGravedad,
      final Sintomas sintomas,
      final Medicamentos medicamentos,
      final boolean esContagiosa,
      final boolean esCubiertaPorPos,
      final boolean requiereIncapacidad) {
    return builder()
        .nombre(nombre).nombreCientifico(nombreCientifico).nivelGravedad(nivelGravedad)
        .sintomas(sintomas).medicamentos(medicamentos)
        .esContagiosa(esContagiosa).esCubiertaPorPos(esCubiertaPorPos)
        .requiereIncapacidad(requiereIncapacidad)
        .build();
  }

  /** Returns a new instance with the given id; all other fields are preserved. */
  public EnfermedadModel withId(final EnfermedadId id) {
    return toBuilder().id(id).build();
  }

  /** Returns a new instance with all mutable fields replaced, keeping the same id. */
  public EnfermedadModel update(
      final NombreEnfermedad nombre,
      final NombreCientifico nombreCientifico,
      final NivelGravedadVO nivelGravedad,
      final Sintomas sintomas,
      final Medicamentos medicamentos,
      final boolean esContagiosa,
      final boolean esCubiertaPorPos,
      final boolean requiereIncapacidad) {
    return toBuilder()
        .nombre(nombre).nombreCientifico(nombreCientifico).nivelGravedad(nivelGravedad)
        .sintomas(sintomas).medicamentos(medicamentos)
        .esContagiosa(esContagiosa).esCubiertaPorPos(esCubiertaPorPos)
        .requiereIncapacidad(requiereIncapacidad)
        .build();
  }

  /** Convenience accessor: returns the wrapped {@link NivelGravedad} enum constant. */
  public NivelGravedad getNivelGravedadEnum() {
    return nivelGravedad.value();
  }
}
