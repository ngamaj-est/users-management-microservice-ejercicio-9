package com.jcaa.usersmanagement.application.port.in;

import com.jcaa.usersmanagement.application.service.dto.command.DeleteEnfermedadCommand;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public interface DeleteEnfermedadUseCase {
  void execute(@NotNull @Valid DeleteEnfermedadCommand command);
}
