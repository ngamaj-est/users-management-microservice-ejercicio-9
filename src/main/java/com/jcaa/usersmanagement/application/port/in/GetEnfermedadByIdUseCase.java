package com.jcaa.usersmanagement.application.port.in;

import com.jcaa.usersmanagement.application.service.dto.query.GetEnfermedadByIdQuery;
import com.jcaa.usersmanagement.domain.model.EnfermedadModel;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public interface GetEnfermedadByIdUseCase {
  EnfermedadModel execute(@NotNull @Valid GetEnfermedadByIdQuery query);
}

