package com.jcaa.usersmanagement.domain.valueobject;

import static org.junit.jupiter.api.Assertions.*;

import com.jcaa.usersmanagement.domain.exception.InvalidNombreCientificoException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class NombreCientificoTest {

  // --- Happy path ---

  @ParameterizedTest
  @ValueSource(strings = {"Influenza", "  SARS-CoV-2  ", "Varicella zoster\t", "Rhinovirus"})
  @DisplayName("Should create NombreCientifico with trimmed valid value")
  void shouldCreateNombreCientificoWithTrimmedValue(final String input) {
    final NombreCientifico vo = new NombreCientifico(input);
    assertEquals(input.trim(), vo.value());
    assertEquals(input.trim(), vo.toString());
  }

  @Test
  @DisplayName("Should accept a nombre cientifico at exactly the maximum length (100 chars)")
  void shouldAcceptNombreCientificoAtMaximumLength() {
    final String maxLength = "A".repeat(100);
    assertDoesNotThrow(() -> new NombreCientifico(maxLength));
  }

  // --- Flujo con excepciones y ramas de validación ---

  @Test
  @DisplayName("Should throw NullPointerException when value is null")
  void shouldThrowNullPointerExceptionWhenValueIsNull() {
    assertThrows(NullPointerException.class, () -> new NombreCientifico(null));
  }

  @ParameterizedTest
  @ValueSource(strings = {"", "   ", "\t", "\n", "\r", "\f", "\b"})
  @DisplayName("Should throw InvalidNombreCientificoException when value is blank or empty")
  void shouldThrowExceptionWhenValueIsBlankOrEmpty(final String input) {
    assertThrows(InvalidNombreCientificoException.class, () -> new NombreCientifico(input));
  }

  @ParameterizedTest
  @ValueSource(strings = {"Gr", "  Ab  ", "Xy\t"})
  @DisplayName("Should throw InvalidNombreCientificoException when value is too short (< 3 chars)")
  void shouldThrowExceptionWhenValueIsTooShort(final String input) {
    assertThrows(InvalidNombreCientificoException.class, () -> new NombreCientifico(input));
  }

  @Test
  @DisplayName("Should throw InvalidNombreCientificoException when value exceeds 100 chars")
  void shouldThrowExceptionWhenValueExceedsMaximumLength() {
    final String tooLong = "A".repeat(101);
    assertThrows(InvalidNombreCientificoException.class, () -> new NombreCientifico(tooLong));
  }
}
