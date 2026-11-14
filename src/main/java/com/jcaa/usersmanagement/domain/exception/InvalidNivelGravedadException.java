package com.jcaa.usersmanagement.domain.exception;

public final class InvalidNivelGravedadException extends DomainException {

  private static final String MESSAGE_INVALID =
      "The nivel de gravedad '%s' is not valid. Allowed values are: LEVE, MODERADA, GRAVE, CRITICA.";
  private static final String MESSAGE_NULL = "The nivel de gravedad must not be null.";

  private InvalidNivelGravedadException(final String message) {
    super(message);
  }

  public static InvalidNivelGravedadException becauseValueIsInvalid(final String value) {
    return new InvalidNivelGravedadException(String.format(MESSAGE_INVALID, value));
  }

  public static InvalidNivelGravedadException becauseValueIsNull() {
    return new InvalidNivelGravedadException(MESSAGE_NULL);
  }
}
