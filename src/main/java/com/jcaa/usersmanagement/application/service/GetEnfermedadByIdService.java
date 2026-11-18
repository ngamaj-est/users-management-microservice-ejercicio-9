package com.jcaa.usersmanagement.application.service;

import com.jcaa.usersmanagement.application.port.in.GetEnfermedadByIdUseCase;
import com.jcaa.usersmanagement.application.port.out.GetEnfermedadByIdPort;
import com.jcaa.usersmanagement.application.service.dto.query.GetEnfermedadByIdQuery;
import com.jcaa.usersmanagement.application.service.mapper.EnfermedadApplicationMapper;
import com.jcaa.usersmanagement.domain.exception.EnfermedadNotFoundException;
import com.jcaa.usersmanagement.domain.model.EnfermedadModel;
import com.jcaa.usersmanagement.domain.valueobject.EnfermedadId;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetEnfermedadByIdService implements GetEnfermedadByIdUseCase {

  private final GetEnfermedadByIdPort getEnfermedadByIdPort;
  private final Validator validator;

  @Override
  public EnfermedadModel execute(final GetEnfermedadByIdQuery query) {
    validateQuery(query);

    final EnfermedadId id = EnfermedadApplicationMapper.fromQueryToEnfermedadId(query);
    return getEnfermedadByIdPort
        .getById(id)
        .orElseThrow(() -> EnfermedadNotFoundException.becauseIdWasNotFound(id.value()));
  }

  private void validateQuery(final GetEnfermedadByIdQuery query) {
    final Set<ConstraintViolation<GetEnfermedadByIdQuery>> violations = validator.validate(query);
    if (!violations.isEmpty()) {
      throw new ConstraintViolationException(violations);
    }
  }
}
