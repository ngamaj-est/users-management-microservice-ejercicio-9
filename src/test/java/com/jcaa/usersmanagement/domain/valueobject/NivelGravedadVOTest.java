package com.jcaa.usersmanagement.domain.valueobject;

import static org.junit.jupiter.api.Assertions.*;

import com.jcaa.usersmanagement.domain.enums.NivelGravedad;
import com.jcaa.usersmanagement.domain.exception.InvalidNivelGravedadException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;

class NivelGravedadVOTest {

  // --- Happy path: canonical constructor ---

  @ParameterizedTest
  @EnumSource(NivelGravedad.class)
  @DisplayName("Should create NivelGravedadVO from every NivelGravedad constant")
  void shouldCreateFromEveryEnumConstant(final NivelGravedad nivel) {
    final NivelGravedadVO vo = new NivelGravedadVO(nivel);
    assertEquals(nivel, vo.value());
    assertEquals(nivel.name(), vo.toString());
  }

  // --- Happy path: fromString factory ---

  @ParameterizedTest
  @ValueSource(strings = {"LEVE", "leve", "Leve", "MODERADA", "Moderada", "GRAVE", "grave", "CRITICA", "Critica"})
  @DisplayName("Should create NivelGravedadVO from valid String (case-insensitive)")
  void shouldCreateFromValidString(final String raw) {
    assertDoesNotThrow(() -> NivelGravedadVO.fromString(raw));
  }

  @Test
  @DisplayName("Should resolve correct enum constant from String")
  void shouldResolveCorrectEnumFromString() {
    assertEquals(NivelGravedad.LEVE, NivelGravedadVO.fromString("leve").value());
    assertEquals(NivelGravedad.MODERADA, NivelGravedadVO.fromString("Moderada").value());
    assertEquals(NivelGravedad.GRAVE, NivelGravedadVO.fromString("GRAVE").value());
    assertEquals(NivelGravedad.CRITICA, NivelGravedadVO.fromString("critica").value());
  }

  // --- Flujo con excepciones y ramas de validación ---

  @Test
  @DisplayName("Should throw InvalidNivelGravedadException when canonical constructor receives null")
  void shouldThrowExceptionWhenEnumIsNull() {
    assertThrows(InvalidNivelGravedadException.class, () -> new NivelGravedadVO(null));
  }

  @Test
  @DisplayName("Should throw NullPointerException when fromString receives null")
  void shouldThrowNullPointerExceptionWhenRawIsNull() {
    assertThrows(NullPointerException.class, () -> NivelGravedadVO.fromString(null));
  }

  @ParameterizedTest
  @ValueSource(strings = {"", "   ", "\t", "\n", "SEVERO", "ALTO", "BAJO", "INVALID"})
  @DisplayName("Should throw InvalidNivelGravedadException when fromString receives invalid value")
  void shouldThrowExceptionWhenStringIsInvalid(final String raw) {
    assertThrows(InvalidNivelGravedadException.class, () -> NivelGravedadVO.fromString(raw));
  }
}
