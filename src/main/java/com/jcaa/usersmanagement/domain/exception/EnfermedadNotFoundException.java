package com.jcaa.usersmanagement.domain.exception;

public final class EnfermedadNotFoundException extends DomainException {

  private static final String MESSAGE_BY_ID = "The enfermedad with id '%d' was not found.";
  private static final String MESSAGE_BY_NOMBRE = "The enfermedad with nombre '%s' was not found.";

  private EnfermedadNotFoundException(final String message) {
    super(message);
  }

  public static EnfermedadNotFoundException becauseIdWasNotFound(final Long id) {
    return new EnfermedadNotFoundException(String.format(MESSAGE_BY_ID, id));
  }

  public static EnfermedadNotFoundException becauseNombreWasNotFound(final String nombre) {
    return new EnfermedadNotFoundException(String.format(MESSAGE_BY_NOMBRE, nombre));
  }
}
