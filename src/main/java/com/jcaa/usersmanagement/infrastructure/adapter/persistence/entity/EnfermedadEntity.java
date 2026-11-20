package com.jcaa.usersmanagement.infrastructure.adapter.persistence.entity;

public record EnfermedadEntity(
    Long id,
    String nombre,
    String nombreCientifico,
    String nivelGravedad,
    String sintomas,
    String medicamentos,
    Boolean esContagiosa,
    Boolean esCubiertaPorPos,
    Boolean requiereIncapacidad) {}
