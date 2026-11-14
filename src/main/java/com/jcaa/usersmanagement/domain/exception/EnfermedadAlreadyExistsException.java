package com.jcaa.usersmanagement.domain.exception;

public final class EnfermedadAlreadyExistsException extends DomainException {

  private static final String MESSAGE_NOMBRE_EXISTS = "An enfermedad with nombre '%s' already exists.";

  private EnfermedadAlreadyExistsException(final String message) {
    super(message);
  }

  public static EnfermedadAlreadyExistsException becauseNombreAlreadyExists(final String nombre) {
    return new EnfermedadAlreadyExistsException(String.format(MESSAGE_NOMBRE_EXISTS, nombre));
  }
}
