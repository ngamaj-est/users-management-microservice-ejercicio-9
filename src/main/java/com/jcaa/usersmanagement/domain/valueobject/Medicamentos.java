package com.jcaa.usersmanagement.domain.valueobject;

import com.jcaa.usersmanagement.domain.exception.InvalidMedicamentosException;
import java.util.Objects;

public record Medicamentos(String value) {

  private static final int MAXIMUM_LENGTH = 200;

  public Medicamentos {
    final String normalizedValue =
        Objects.requireNonNull(value, "Medicamentos cannot be null").trim();
    validateNotEmpty(normalizedValue);
    validateMaximumLength(normalizedValue);
    value = normalizedValue;
  }

  private static void validateNotEmpty(final String normalizedValue) {
    if (normalizedValue.isEmpty()) {
      throw InvalidMedicamentosException.becauseValueIsEmpty();
    }
  }

  private static void validateMaximumLength(final String normalizedValue) {
    if (normalizedValue.length() > MAXIMUM_LENGTH) {
      throw InvalidMedicamentosException.becauseLengthIsTooLong(MAXIMUM_LENGTH);
    }
  }

  @Override
  public String toString() {
    return value;
  }
}
