package com.jcaa.usersmanagement.domain.exception;

public final class InvalidSintomasException extends DomainException {

  private static final String MESSAGE_EMPTY = "The sintomas must not be empty.";
  private static final String MESSAGE_TOO_LONG = "The sintomas must not exceed %d characters.";

  private InvalidSintomasException(final String message) {
    super(message);
  }

  public static InvalidSintomasException becauseValueIsEmpty() {
    return new InvalidSintomasException(MESSAGE_EMPTY);
  }

  public static InvalidSintomasException becauseLengthIsTooLong(final int maximumLength) {
    return new InvalidSintomasException(String.format(MESSAGE_TOO_LONG, maximumLength));
  }
}
