package com.jcaa.usersmanagement.domain.valueobject;

import com.jcaa.usersmanagement.domain.exception.InvalidEnfermedadIdException;
import java.util.Objects;

public record EnfermedadId(Long value) {

  public EnfermedadId {
    Objects.requireNonNull(value, "EnfermedadId cannot be null");
    validatePositive(value);
  }

  private static void validatePositive(final Long value) {
    if (value <= 0) {
      throw InvalidEnfermedadIdException.becauseValueIsNegativeOrZero(value);
    }
  }

  @Override
  public String toString() {
    return String.valueOf(value);
  }
}
