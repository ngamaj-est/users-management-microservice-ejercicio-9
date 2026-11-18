package com.jcaa.usersmanagement.application.port.out;

import com.jcaa.usersmanagement.domain.model.EnfermedadModel;
import com.jcaa.usersmanagement.domain.valueobject.EnfermedadId;
import java.util.Optional;

/** Persistence output port: looks up an enfermedad by its primary key. */
public interface GetEnfermedadByIdPort {
  Optional<EnfermedadModel> getById(EnfermedadId id);
}
