package com.jcaa.usersmanagement.application.port.out;

import com.jcaa.usersmanagement.domain.model.EnfermedadModel;

/** Persistence output port: replaces all fields of an existing {@link EnfermedadModel}. */
public interface UpdateEnfermedadPort {
  EnfermedadModel update(EnfermedadModel enfermedad);
}
