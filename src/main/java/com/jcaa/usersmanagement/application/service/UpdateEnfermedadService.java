package com.jcaa.usersmanagement.application.service;

import com.jcaa.usersmanagement.application.port.in.UpdateEnfermedadUseCase;
import com.jcaa.usersmanagement.application.port.out.GetEnfermedadByIdPort;
import com.jcaa.usersmanagement.application.port.out.GetEnfermedadByNombrePort;
import com.jcaa.usersmanagement.application.port.out.UpdateEnfermedadPort;
import com.jcaa.usersmanagement.application.service.dto.command.UpdateEnfermedadCommand;
import com.jcaa.usersmanagement.application.service.mapper.EnfermedadApplicationMapper;
import com.jcaa.usersmanagement.domain.exception.EnfermedadAlreadyExistsException;
import com.jcaa.usersmanagement.domain.exception.EnfermedadNotFoundException;
import com.jcaa.usersmanagement.domain.model.EnfermedadModel;
import com.jcaa.usersmanagement.domain.valueobject.EnfermedadId;
import com.jcaa.usersmanagement.domain.valueobject.NombreEnfermedad;
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
public class UpdateEnfermedadService implements UpdateEnfermedadUseCase {

  private final UpdateEnfermedadPort updateEnfermedadPort;
  private final GetEnfermedadByIdPort getEnfermedadByIdPort;
  private final GetEnfermedadByNombrePort getEnfermedadByNombrePort;
  private final Validator validator;

  @Override
  public EnfermedadModel execute(final UpdateEnfermedadCommand command) {
    validateCommand(command);

    final EnfermedadId id = new EnfermedadId(command.id());
    findExistingEnfermedadOrFail(id);

    final NombreEnfermedad newNombre = new NombreEnfermedad(command.nombre());
    ensureNombreIsNotTakenByAnother(newNombre, id);

    final EnfermedadModel enfermedadToUpdate =
        EnfermedadApplicationMapper.fromUpdateCommandToModel(command);

    log.info("Updating enfermedad with id '{}'", id);
    return updateEnfermedadPort.update(enfermedadToUpdate);
  }

  private void validateCommand(final UpdateEnfermedadCommand command) {
    final Set<ConstraintViolation<UpdateEnfermedadCommand>> violations = validator.validate(command);
    if (!violations.isEmpty()) {
      throw new ConstraintViolationException(violations);
    }
  }

  private void findExistingEnfermedadOrFail(final EnfermedadId id) {
    getEnfermedadByIdPort
        .getById(id)
        .orElseThrow(() -> EnfermedadNotFoundException.becauseIdWasNotFound(id.value()));
  }

  private void ensureNombreIsNotTakenByAnother(
      final NombreEnfermedad nombre, final EnfermedadId ownerId) {
    getEnfermedadByNombrePort
        .getByNombre(nombre)
        .ifPresent(
            found -> {
              if (!found.getId().equals(ownerId)) {
                throw EnfermedadAlreadyExistsException.becauseNombreAlreadyExists(nombre.value());
              }
            });
  }
}
