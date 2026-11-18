package com.jcaa.usersmanagement.application.service;

import com.jcaa.usersmanagement.application.port.in.DeleteEnfermedadUseCase;
import com.jcaa.usersmanagement.application.port.out.DeleteEnfermedadPort;
import com.jcaa.usersmanagement.application.port.out.GetEnfermedadByIdPort;
import com.jcaa.usersmanagement.application.service.dto.command.DeleteEnfermedadCommand;
import com.jcaa.usersmanagement.application.service.mapper.EnfermedadApplicationMapper;
import com.jcaa.usersmanagement.domain.exception.EnfermedadNotFoundException;
import com.jcaa.usersmanagement.domain.valueobject.EnfermedadId;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class DeleteEnfermedadService implements DeleteEnfermedadUseCase {

  private final DeleteEnfermedadPort deleteEnfermedadPort;
  private final GetEnfermedadByIdPort getEnfermedadByIdPort;
  private final Validator validator;

  @Override
  public void execute(final DeleteEnfermedadCommand command) {
    validateCommand(command);

    final EnfermedadId id = EnfermedadApplicationMapper.fromDeleteCommandToEnfermedadId(command);
    ensureEnfermedadExists(id);

    log.info("Deleting enfermedad with id '{}'", id);
    deleteEnfermedadPort.delete(id);
  }

  private void validateCommand(final DeleteEnfermedadCommand command) {
    final Set<ConstraintViolation<DeleteEnfermedadCommand>> violations = validator.validate(command);
    if (!violations.isEmpty()) {
      throw new ConstraintViolationException(violations);
    }
  }

  private void ensureEnfermedadExists(final EnfermedadId id) {
    getEnfermedadByIdPort
        .getById(id)
        .orElseThrow(() -> EnfermedadNotFoundException.becauseIdWasNotFound(id.value()));
  }
}
