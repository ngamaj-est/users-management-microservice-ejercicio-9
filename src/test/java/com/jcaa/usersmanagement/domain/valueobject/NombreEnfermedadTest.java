package com.jcaa.usersmanagement.domain.valueobject;

import static org.junit.jupiter.api.Assertions.*;

import com.jcaa.usersmanagement.domain.exception.InvalidNombreEnfermedadException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class NombreEnfermedadTest {

  // --- Happy path ---

  @ParameterizedTest
  @ValueSource(strings = {"Gripe", "  Gripe común  ", "COVID-19\t", "Hipertensión arterial"})
  @DisplayName("Should create NombreEnfermedad with trimmed valid value")
  void shouldCreateNombreEnfermedadWithTrimmedValue(final String input) {
    final NombreEnfermedad vo = new NombreEnfermedad(input);
    assertEquals(input.trim(), vo.value());
    assertEquals(input.trim(), vo.toString());
  }

  @Test
  @DisplayName("Should accept a nombre at exactly the maximum length (100 chars)")
  void shouldAcceptNombreAtMaximumLength() {
    final String maxLength = "A".repeat(100);
    assertDoesNotThrow(() -> new NombreEnfermedad(maxLength));
  }

  // --- Flujo con excepciones y ramas de validación ---

  @Test
  @DisplayName("Should throw NullPointerException when value is null")
  void shouldThrowNullPointerExceptionWhenValueIsNull() {
    assertThrows(NullPointerException.class, () -> new NombreEnfermedad(null));
  }

  @ParameterizedTest
  @ValueSource(strings = {"", "   ", "\t", "\n", "\r", "\f", "\b"})
  @DisplayName("Should throw InvalidNombreEnfermedadException when value is blank or empty")
  void shouldThrowExceptionWhenValueIsBlankOrEmpty(final String input) {
    assertThrows(InvalidNombreEnfermedadException.class, () -> new NombreEnfermedad(input));
  }

  @ParameterizedTest
  @ValueSource(strings = {"Gr", "  Gr  ", "Ab\t"})
  @DisplayName("Should throw InvalidNombreEnfermedadException when value is too short (< 3 chars)")
  void shouldThrowExceptionWhenValueIsTooShort(final String input) {
    assertThrows(InvalidNombreEnfermedadException.class, () -> new NombreEnfermedad(input));
  }

  @Test
  @DisplayName("Should throw InvalidNombreEnfermedadException when value exceeds 100 chars")
  void shouldThrowExceptionWhenValueExceedsMaximumLength() {
    final String tooLong = "A".repeat(101);
    assertThrows(InvalidNombreEnfermedadException.class, () -> new NombreEnfermedad(tooLong));
  }
}
