package com.jcaa.usersmanagement.domain.enums;

import static org.junit.jupiter.api.Assertions.*;

import com.jcaa.usersmanagement.domain.exception.InvalidNivelGravedadException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class NivelGravedadTest {

  // --- Happy path ---

  @Test
  @DisplayName("Should return correct NivelGravedad for valid exact-case input")
  void shouldReturnCorrectNivelGravedadForValidInput() {
    assertEquals(NivelGravedad.LEVE, NivelGravedad.fromString("LEVE"));
    assertEquals(NivelGravedad.MODERADA, NivelGravedad.fromString("MODERADA"));
    assertEquals(NivelGravedad.GRAVE, NivelGravedad.fromString("GRAVE"));
    assertEquals(NivelGravedad.CRITICA, NivelGravedad.fromString("CRITICA"));
  }

  @Test
  @DisplayName("Should return correct NivelGravedad for valid mixed-case input (case-insensitive)")
  void shouldReturnCorrectNivelGravedadForMixedCaseInput() {
    assertEquals(NivelGravedad.LEVE, NivelGravedad.fromString("leve"));
    assertEquals(NivelGravedad.MODERADA, NivelGravedad.fromString("Moderada"));
    assertEquals(NivelGravedad.GRAVE, NivelGravedad.fromString("Grave"));
    assertEquals(NivelGravedad.CRITICA, NivelGravedad.fromString("critica"));
  }

  // --- Flujo con excepciones y ramas de validación ---

  @Test
  @DisplayName("Should throw InvalidNivelGravedadException when input is null")
  void shouldThrowExceptionWhenInputIsNull() {
    assertThrows(InvalidNivelGravedadException.class, () -> NivelGravedad.fromString(null));
  }

  @ParameterizedTest
  @ValueSource(strings = {"", "   ", "\t", "\n", "\r", "\f", "\b", "INVALID_NIVEL", "SEVERO", "ALTO"})
  @DisplayName("Should throw InvalidNivelGravedadException when input is empty or invalid")
  void shouldThrowExceptionWhenInputIsEmptyOrInvalid(final String input) {
    assertThrows(InvalidNivelGravedadException.class, () -> NivelGravedad.fromString(input));
  }
}
