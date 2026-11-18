package com.jcaa.usersmanagement.application.service;

import com.jcaa.usersmanagement.application.port.in.GetEnfermedadByNombreUseCase;
import com.jcaa.usersmanagement.application.port.out.GetEnfermedadByNombrePort;
import com.jcaa.usersmanagement.application.service.dto.query.GetEnfermedadByNombreQuery;
import com.jcaa.usersmanagement.application.service.mapper.EnfermedadApplicationMapper;
import com.jcaa.usersmanagement.domain.exception.EnfermedadNotFoundException;
import com.jcaa.usersmanagement.domain.model.EnfermedadModel;
import com.jcaa.usersmanagement.domain.valueobject.NombreEnfermedad;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetEnfermedadByNombreService implements GetEnfermedadByNombreUseCase {

  private final GetEnfermedadByNombrePort getEnfermedadByNombrePort;
  private final Validator validator;

  @Override
  public EnfermedadModel execute(final GetEnfermedadByNombreQuery query) {
    validateQuery(query);

    final NombreEnfermedad nombre =
        EnfermedadApplicationMapper.fromQueryToNombreEnfermedad(query);
    return getEnfermedadByNombrePort
        .getByNombre(nombre)
        .orElseThrow(() -> EnfermedadNotFoundException.becauseNombreWasNotFound(nombre.value()));
  }

  private void validateQuery(final GetEnfermedadByNombreQuery query) {
    final Set<ConstraintViolation<GetEnfermedadByNombreQuery>> violations = validator.validate(query);
    if (!violations.isEmpty()) {
      throw new ConstraintViolationException(violations);
    }
  }
}
