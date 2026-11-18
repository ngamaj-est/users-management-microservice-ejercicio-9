package com.jcaa.usersmanagement.domain.enums;

import com.jcaa.usersmanagement.domain.exception.InvalidNivelGravedadException;

public enum NivelGravedad {
  LEVE,
  MODERADA,
  GRAVE,
  CRITICA;

  public static NivelGravedad fromString(final String value) {
    if (value == null) {
      throw InvalidNivelGravedadException.becauseValueIsNull();
    }
    for (final NivelGravedad nivel : values()) {
      if (nivel.name().equalsIgnoreCase(value)) {
        return nivel;
      }
    }
    throw InvalidNivelGravedadException.becauseValueIsInvalid(value);
  }
}
