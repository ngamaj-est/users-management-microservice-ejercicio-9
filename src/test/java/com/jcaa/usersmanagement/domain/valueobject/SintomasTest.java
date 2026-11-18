package com.jcaa.usersmanagement.domain.valueobject;

import static org.junit.jupiter.api.Assertions.*;

import com.jcaa.usersmanagement.domain.exception.InvalidSintomasException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class SintomasTest {

  // --- Happy path ---

  @ParameterizedTest
  @ValueSource(strings = {
      "Fiebre, tos, congestión nasal",
      "  Fiebre, tos seca, dificultad respiratoria  ",
      "Erupción cutánea, picazón, fiebre\t"
  })
  @DisplayName("Should create Sintomas with trimmed valid value")
  void shouldCreateSintomasWithTrimmedValue(final String input) {
    final Sintomas vo = new Sintomas(input);
    assertEquals(input.trim(), vo.value());
    assertEquals(input.trim(), vo.toString());
  }

  @Test
  @DisplayName("Should accept sintomas at exactly the maximum length (500 chars)")
  void shouldAcceptSintomasAtMaximumLength() {
    final String maxLength = "A".repeat(500);
    assertDoesNotThrow(() -> new Sintomas(maxLength));
  }

  // --- Flujo con excepciones y ramas de validación ---

  @Test
  @DisplayName("Should throw NullPointerException when value is null")
  void shouldThrowNullPointerExceptionWhenValueIsNull() {
    assertThrows(NullPointerException.class, () -> new Sintomas(null));
  }

  @ParameterizedTest
  @ValueSource(strings = {"", "   ", "\t", "\n", "\r", "\f", "\b"})
  @DisplayName("Should throw InvalidSintomasException when value is blank or empty")
  void shouldThrowExceptionWhenValueIsBlankOrEmpty(final String input) {
    assertThrows(InvalidSintomasException.class, () -> new Sintomas(input));
  }

  @Test
  @DisplayName("Should throw InvalidSintomasException when value exceeds 500 chars")
  void shouldThrowExceptionWhenValueExceedsMaximumLength() {
    final String tooLong = "A".repeat(501);
    assertThrows(InvalidSintomasException.class, () -> new Sintomas(tooLong));
  }
}
