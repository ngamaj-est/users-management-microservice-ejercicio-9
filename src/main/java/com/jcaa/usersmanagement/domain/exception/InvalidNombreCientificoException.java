package com.jcaa.usersmanagement.domain.exception;

public final class InvalidNombreCientificoException extends DomainException {

  private static final String MESSAGE_EMPTY = "The enfermedad nombre cientifico must not be empty.";
  private static final String MESSAGE_TOO_SHORT = "The enfermedad nombre cientifico must have at least %d characters.";
  private static final String MESSAGE_TOO_LONG = "The enfermedad nombre cientifico must not exceed %d characters.";

  private InvalidNombreCientificoException(final String message) {
    super(message);
  }

  public static InvalidNombreCientificoException becauseValueIsEmpty() {
    return new InvalidNombreCientificoException(MESSAGE_EMPTY);
  }

  public static InvalidNombreCientificoException becauseLengthIsTooShort(final int minimumLength) {
    return new InvalidNombreCientificoException(String.format(MESSAGE_TOO_SHORT, minimumLength));
  }

  public static InvalidNombreCientificoException becauseLengthIsTooLong(final int maximumLength) {
    return new InvalidNombreCientificoException(String.format(MESSAGE_TOO_LONG, maximumLength));
  }
}
