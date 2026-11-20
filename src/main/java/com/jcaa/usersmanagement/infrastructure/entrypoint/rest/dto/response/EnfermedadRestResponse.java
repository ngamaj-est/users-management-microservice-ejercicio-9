package com.jcaa.usersmanagement.infrastructure.entrypoint.rest.dto.response;

public record EnfermedadRestResponse(
    Long id,
    String nombre,
    String nombreCientifico,
    String nivelGravedad,
    String sintomas,
    String medicamentos,
    Boolean esContagiosa,
    Boolean esCubiertaPorPos,
    Boolean requiereIncapacidad) {}
