package com.jcaa.usersmanagement.domain.exception;

public final class InvalidEnfermedadIdException extends DomainException {

  private static final String MESSAGE_NULL = "The enfermedad id must not be null.";
  private static final String MESSAGE_NEGATIVE_OR_ZERO = "The enfermedad id '%d' must be greater than zero.";

  private InvalidEnfermedadIdException(final String message) {
    super(message);
  }

  public static InvalidEnfermedadIdException becauseValueIsNull() {
    return new InvalidEnfermedadIdException(MESSAGE_NULL);
  }

  public static InvalidEnfermedadIdException becauseValueIsNegativeOrZero(final Long id) {
    return new InvalidEnfermedadIdException(String.format(MESSAGE_NEGATIVE_OR_ZERO, id));
  }
}
