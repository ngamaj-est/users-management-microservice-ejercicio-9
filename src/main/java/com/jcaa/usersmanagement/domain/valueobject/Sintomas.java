package com.jcaa.usersmanagement.domain.valueobject;

import com.jcaa.usersmanagement.domain.exception.InvalidSintomasException;
import java.util.Objects;

public record Sintomas(String value) {

  private static final int MAXIMUM_LENGTH = 500;

  public Sintomas {
    final String normalizedValue =
        Objects.requireNonNull(value, "Sintomas cannot be null").trim();
    validateNotEmpty(normalizedValue);
    validateMaximumLength(normalizedValue);
    value = normalizedValue;
  }

  private static void validateNotEmpty(final String normalizedValue) {
    if (normalizedValue.isEmpty()) {
      throw InvalidSintomasException.becauseValueIsEmpty();
    }
  }

  private static void validateMaximumLength(final String normalizedValue) {
    if (normalizedValue.length() > MAXIMUM_LENGTH) {
      throw InvalidSintomasException.becauseLengthIsTooLong(MAXIMUM_LENGTH);
    }
  }

  @Override
  public String toString() {
    return value;
  }
}
