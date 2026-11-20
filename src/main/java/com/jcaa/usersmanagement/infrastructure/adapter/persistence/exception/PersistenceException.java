package com.jcaa.usersmanagement.infrastructure.adapter.persistence.exception;

public final class PersistenceException extends RuntimeException {

  private static final String MESSAGE_SAVE = "Failed to save user with ID: '%s'.";
  private static final String MESSAGE_UPDATE = "Failed to update user with ID: '%s'.";
  private static final String MESSAGE_FIND = "Failed to find user with ID: '%s'.";
  private static final String MESSAGE_EMAIL = "Failed to find user with email: '%s'.";
  private static final String MESSAGE_ALL = "Failed to retrieve all users.";
  private static final String MESSAGE_DELETE = "Failed to delete user with ID: '%s'.";
  private static final String MESSAGE_CONNECTION = "Could not establish database connection.";

  private PersistenceException(final String message, final Throwable cause) {
    super(message, cause);
  }

  public static PersistenceException becauseSaveFailed(final String userId, final Throwable cause) {
    return new PersistenceException(String.format(MESSAGE_SAVE, userId), cause);
  }

  public static PersistenceException becauseUpdateFailed(
      final String userId, final Throwable cause) {
    return new PersistenceException(String.format(MESSAGE_UPDATE, userId), cause);
  }

  public static PersistenceException becauseFindByIdFailed(
      final String userId, final Throwable cause) {
    return new PersistenceException(String.format(MESSAGE_FIND, userId), cause);
  }

  public static PersistenceException becauseFindByEmailFailed(
      final String email, final Throwable cause) {
    return new PersistenceException(String.format(MESSAGE_EMAIL, email), cause);
  }

  public static PersistenceException becauseFindAllFailed(final Throwable cause) {
    return new PersistenceException(MESSAGE_ALL, cause);
  }

  public static PersistenceException becauseDeleteFailed(
      final String userId, final Throwable cause) {
    return new PersistenceException(String.format(MESSAGE_DELETE, userId), cause);
  }

  public static PersistenceException becauseConnectionFailed(final Throwable cause) {
    return new PersistenceException(MESSAGE_CONNECTION, cause);
  }

  public static PersistenceException becauseSaveEnfermedadFailed(
      final String nombre, final Throwable cause) {
    return new PersistenceException(
        String.format("Failed to save enfermedad with nombre: '%s'.", nombre), cause);
  }

  public static PersistenceException becauseUpdateEnfermedadFailed(
      final Long id, final Throwable cause) {
    return new PersistenceException(
        String.format("Failed to update enfermedad with ID: '%d'.", id), cause);
  }

  public static PersistenceException becauseFindEnfermedadByIdFailed(
      final Long id, final Throwable cause) {
    return new PersistenceException(
        String.format("Failed to find enfermedad with ID: '%d'.", id), cause);
  }

  public static PersistenceException becauseFindEnfermedadByNombreFailed(
      final String nombre, final Throwable cause) {
    return new PersistenceException(
        String.format("Failed to find enfermedad with nombre: '%s'.", nombre), cause);
  }

  public static PersistenceException becauseFindAllEnfermedadesFailed(final Throwable cause) {
    return new PersistenceException("Failed to retrieve all enfermedades.", cause);
  }

  public static PersistenceException becauseDeleteEnfermedadFailed(
      final Long id, final Throwable cause) {
    return new PersistenceException(
        String.format("Failed to delete enfermedad with ID: '%d'.", id), cause);
  }
}
