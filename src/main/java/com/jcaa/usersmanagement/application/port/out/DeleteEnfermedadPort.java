package com.jcaa.usersmanagement.application.port.out;

import com.jcaa.usersmanagement.domain.valueobject.EnfermedadId;

/** Persistence output port: removes the enfermedad identified by the given id. */
public interface DeleteEnfermedadPort {
  void delete(EnfermedadId id);
}
