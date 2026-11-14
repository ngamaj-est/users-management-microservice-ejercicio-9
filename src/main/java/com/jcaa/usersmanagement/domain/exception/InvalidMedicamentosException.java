package com.jcaa.usersmanagement.domain.exception;

public final class InvalidMedicamentosException extends DomainException {

  private static final String MESSAGE_EMPTY = "The medicamentos must not be empty.";
  private static final String MESSAGE_TOO_LONG = "The medicamentos must not exceed %d characters.";

  private InvalidMedicamentosException(final String message) {
    super(message);
  }

  public static InvalidMedicamentosException becauseValueIsEmpty() {
    return new InvalidMedicamentosException(MESSAGE_EMPTY);
  }

  public static InvalidMedicamentosException becauseLengthIsTooLong(final int maximumLength) {
    return new InvalidMedicamentosException(String.format(MESSAGE_TOO_LONG, maximumLength));
  }
}
