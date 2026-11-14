package com.jcaa.usersmanagement.domain.exception;

public final class InvalidNombreEnfermedadException extends DomainException {

  private static final String MESSAGE_EMPTY = "The enfermedad nombre must not be empty.";
  private static final String MESSAGE_TOO_SHORT = "The enfermedad nombre must have at least %d characters.";
  private static final String MESSAGE_TOO_LONG = "The enfermedad nombre must not exceed %d characters.";

  private InvalidNombreEnfermedadException(final String message) {
    super(message);
  }

  public static InvalidNombreEnfermedadException becauseValueIsEmpty() {
    return new InvalidNombreEnfermedadException(MESSAGE_EMPTY);
  }

  public static InvalidNombreEnfermedadException becauseLengthIsTooShort(final int minimumLength) {
    return new InvalidNombreEnfermedadException(String.format(MESSAGE_TOO_SHORT, minimumLength));
  }

  public static InvalidNombreEnfermedadException becauseLengthIsTooLong(final int maximumLength) {
    return new InvalidNombreEnfermedadException(String.format(MESSAGE_TOO_LONG, maximumLength));
  }
}
