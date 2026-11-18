package com.jcaa.usersmanagement.domain.valueobject;

import com.jcaa.usersmanagement.domain.enums.NivelGravedad;
import com.jcaa.usersmanagement.domain.exception.InvalidNivelGravedadException;
import java.util.Objects;

public record NivelGravedadVO(NivelGravedad value) {

  public NivelGravedadVO {
    if (value == null) {
      throw InvalidNivelGravedadException.becauseValueIsNull();
    }
  }

  /**
   * Factory method that parses a raw String into the wrapped enum constant.
   * Delegates all parsing and exception logic to {@link NivelGravedad#fromString(String)}.
   */
  public static NivelGravedadVO fromString(final String raw) {
    Objects.requireNonNull(raw, "NivelGravedadVO raw value cannot be null");
    return new NivelGravedadVO(NivelGravedad.fromString(raw));
  }

  @Override
  public String toString() {
    return value.name();
  }
}
