package com.jcaa.usersmanagement.infrastructure.adapter.persistence.dto;

public record EnfermedadPersistenceDto(
    Long id,
    String nombre,
    String nombreCientifico,
    String nivelGravedad,
    String sintomas,
    String medicamentos,
    Boolean esContagiosa,
    Boolean esCubiertaPorPos,
    Boolean requiereIncapacidad) {}
