package com.jcaa.usersmanagement.domain.valueobject;

import com.jcaa.usersmanagement.domain.exception.InvalidNombreEnfermedadException;
import java.util.Objects;

public record NombreEnfermedad(String value) {

  private static final int MINIMUM_LENGTH = 3;
  private static final int MAXIMUM_LENGTH = 100;

  public NombreEnfermedad {
    final String normalizedValue =
        Objects.requireNonNull(value, "NombreEnfermedad cannot be null").trim();
    validateNotEmpty(normalizedValue);
    validateMinimumLength(normalizedValue);
    validateMaximumLength(normalizedValue);
    value = normalizedValue;
  }

  private static void validateNotEmpty(final String normalizedValue) {
    if (normalizedValue.isEmpty()) {
      throw InvalidNombreEnfermedadException.becauseValueIsEmpty();
    }
  }

  private static void validateMinimumLength(final String normalizedValue) {
    if (normalizedValue.length() < MINIMUM_LENGTH) {
      throw InvalidNombreEnfermedadException.becauseLengthIsTooShort(MINIMUM_LENGTH);
    }
  }

  private static void validateMaximumLength(final String normalizedValue) {
    if (normalizedValue.length() > MAXIMUM_LENGTH) {
      throw InvalidNombreEnfermedadException.becauseLengthIsTooLong(MAXIMUM_LENGTH);
    }
  }

  @Override
  public String toString() {
    return value;
  }
}
