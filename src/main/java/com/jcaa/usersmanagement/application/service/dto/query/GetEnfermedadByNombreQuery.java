package com.jcaa.usersmanagement.application.service.dto.query;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record GetEnfermedadByNombreQuery(
    @NotBlank(message = "nombre must not be blank")
        @Size(min = 3, max = 100, message = "nombre must be between 3 and 100 characters")
        String nombre) {}
