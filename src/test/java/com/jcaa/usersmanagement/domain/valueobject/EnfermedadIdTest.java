package com.jcaa.usersmanagement.domain.valueobject;

import static org.junit.jupiter.api.Assertions.*;

import com.jcaa.usersmanagement.domain.exception.InvalidEnfermedadIdException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class EnfermedadIdTest {

  // --- Happy path ---

  @ParameterizedTest
  @ValueSource(longs = {1L, 100L, Long.MAX_VALUE})
  @DisplayName("Should create EnfermedadId with valid positive value")
  void shouldCreateEnfermedadIdWithValidValue(final long id) {
    final EnfermedadId enfermedadId = new EnfermedadId(id);
    assertEquals(id, enfermedadId.value());
    assertEquals(String.valueOf(id), enfermedadId.toString());
  }

  // --- Flujo con excepciones y ramas de validación ---

  @Test
  @DisplayName("Should throw NullPointerException when value is null")
  void shouldThrowNullPointerExceptionWhenValueIsNull() {
    assertThrows(NullPointerException.class, () -> new EnfermedadId(null));
  }

  @ParameterizedTest
  @ValueSource(longs = {0L, -1L, -100L, Long.MIN_VALUE})
  @DisplayName("Should throw InvalidEnfermedadIdException when value is zero or negative")
  void shouldThrowExceptionWhenValueIsZeroOrNegative(final long id) {
    assertThrows(InvalidEnfermedadIdException.class, () -> new EnfermedadId(id));
  }
}
