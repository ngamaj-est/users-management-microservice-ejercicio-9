package com.jcaa.usersmanagement.application.port.in;

import com.jcaa.usersmanagement.application.service.dto.command.CreateEnfermedadCommand;
import com.jcaa.usersmanagement.domain.model.EnfermedadModel;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public interface CreateEnfermedadUseCase {
  EnfermedadModel execute(@NotNull @Valid CreateEnfermedadCommand command);
}

