package com.jcaa.usersmanagement.domain.valueobject;

import static org.junit.jupiter.api.Assertions.*;

import com.jcaa.usersmanagement.domain.exception.InvalidMedicamentosException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class MedicamentosTest {

  // --- Happy path ---

  @ParameterizedTest
  @ValueSource(strings = {
      "Paracetamol, Loratadina",
      "  Metformina, Insulina  ",
      "Aciclovir, Antihistamínicos\t"
  })
  @DisplayName("Should create Medicamentos with trimmed valid value")
  void shouldCreateMedicamentosWithTrimmedValue(final String input) {
    final Medicamentos vo = new Medicamentos(input);
    assertEquals(input.trim(), vo.value());
    assertEquals(input.trim(), vo.toString());
  }

  @Test
  @DisplayName("Should accept medicamentos at exactly the maximum length (200 chars)")
  void shouldAcceptMedicamentosAtMaximumLength() {
    final String maxLength = "A".repeat(200);
    assertDoesNotThrow(() -> new Medicamentos(maxLength));
  }

  // --- Flujo con excepciones y ramas de validación ---

  @Test
  @DisplayName("Should throw NullPointerException when value is null")
  void shouldThrowNullPointerExceptionWhenValueIsNull() {
    assertThrows(NullPointerException.class, () -> new Medicamentos(null));
  }

  @ParameterizedTest
  @ValueSource(strings = {"", "   ", "\t", "\n", "\r", "\f", "\b"})
  @DisplayName("Should throw InvalidMedicamentosException when value is blank or empty")
  void shouldThrowExceptionWhenValueIsBlankOrEmpty(final String input) {
    assertThrows(InvalidMedicamentosException.class, () -> new Medicamentos(input));
  }

  @Test
  @DisplayName("Should throw InvalidMedicamentosException when value exceeds 200 chars")
  void shouldThrowExceptionWhenValueExceedsMaximumLength() {
    final String tooLong = "A".repeat(201);
    assertThrows(InvalidMedicamentosException.class, () -> new Medicamentos(tooLong));
  }
}
