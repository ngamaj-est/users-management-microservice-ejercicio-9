package com.jcaa.usersmanagement.infrastructure.adapter.persistence.config;

public record DatabaseConfig(
    String host,
    int port,
    String databaseName,
    String username,
    String password,
    String sslMode) {

  public String buildJdbcUrl() {
    return String.format(
        "jdbc:mysql://%s:%d/%s?sslMode=%s&serverTimezone=UTC&allowPublicKeyRetrieval=true&createDatabaseIfNotExist=true",
        host, port, databaseName, sslMode);
  }
}
