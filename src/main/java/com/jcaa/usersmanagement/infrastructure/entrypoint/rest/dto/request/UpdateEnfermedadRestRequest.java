package com.jcaa.usersmanagement.infrastructure.entrypoint.rest.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UpdateEnfermedadRestRequest(
    @NotBlank(message = "nombre must not be blank")
        @Size(min = 3, max = 100, message = "nombre must be between 3 and 100 characters")
        String nombre,
    @NotBlank(message = "nombreCientifico must not be blank")
        @Size(min = 3, max = 100, message = "nombreCientifico must be between 3 and 100 characters")
        String nombreCientifico,
    @NotBlank(message = "nivelGravedad must not be blank")
        String nivelGravedad,
    @NotBlank(message = "sintomas must not be blank")
        @Size(max = 500, message = "sintomas must not exceed 500 characters")
        String sintomas,
    @NotBlank(message = "medicamentos must not be blank")
        @Size(max = 200, message = "medicamentos must not exceed 200 characters")
        String medicamentos,
    @NotNull(message = "esContagiosa must not be null")
        Boolean esContagiosa,
    @NotNull(message = "esCubiertaPorPos must not be null")
        Boolean esCubiertaPorPos,
    @NotNull(message = "requiereIncapacidad must not be null")
        Boolean requiereIncapacidad) {}
