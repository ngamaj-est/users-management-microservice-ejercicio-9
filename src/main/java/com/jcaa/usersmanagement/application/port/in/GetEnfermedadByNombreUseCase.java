package com.jcaa.usersmanagement.application.port.in;

import com.jcaa.usersmanagement.application.service.dto.query.GetEnfermedadByNombreQuery;
import com.jcaa.usersmanagement.domain.model.EnfermedadModel;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public interface GetEnfermedadByNombreUseCase {
  EnfermedadModel execute(@NotNull @Valid GetEnfermedadByNombreQuery query);
}
