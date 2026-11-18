package com.jcaa.usersmanagement.application.service;

import com.jcaa.usersmanagement.application.port.in.CreateEnfermedadUseCase;
import com.jcaa.usersmanagement.application.port.out.GetEnfermedadByNombrePort;
import com.jcaa.usersmanagement.application.port.out.SaveEnfermedadPort;
import com.jcaa.usersmanagement.application.service.dto.command.CreateEnfermedadCommand;
import com.jcaa.usersmanagement.application.service.mapper.EnfermedadApplicationMapper;
import com.jcaa.usersmanagement.domain.exception.EnfermedadAlreadyExistsException;
import com.jcaa.usersmanagement.domain.model.EnfermedadModel;
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
public class CreateEnfermedadService implements CreateEnfermedadUseCase {

  private final SaveEnfermedadPort saveEnfermedadPort;
  private final GetEnfermedadByNombrePort getEnfermedadByNombrePort;
  private final Validator validator;

  @Override
  public EnfermedadModel execute(final CreateEnfermedadCommand command) {
    validateCommand(command);

    final NombreEnfermedad nombre = new NombreEnfermedad(command.nombre());
    ensureNombreIsNotTaken(nombre);

    final EnfermedadModel enfermedadToSave =
        EnfermedadApplicationMapper.fromCreateCommandToModel(command);

    log.info("Creating enfermedad with nombre '{}'", nombre);
    return saveEnfermedadPort.save(enfermedadToSave);
  }

  private void validateCommand(final CreateEnfermedadCommand command) {
    final Set<ConstraintViolation<CreateEnfermedadCommand>> violations = validator.validate(command);
    if (!violations.isEmpty()) {
      throw new ConstraintViolationException(violations);
    }
  }

  private void ensureNombreIsNotTaken(final NombreEnfermedad nombre) {
    getEnfermedadByNombrePort
        .getByNombre(nombre)
        .ifPresent(
            ignored -> {
              throw EnfermedadAlreadyExistsException.becauseNombreAlreadyExists(nombre.value());
            });
  }
}
