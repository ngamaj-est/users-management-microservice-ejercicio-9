package com.jcaa.usersmanagement.application.port.out;

import com.jcaa.usersmanagement.domain.model.EnfermedadModel;

/** Persistence output port: persists a new {@link EnfermedadModel} and returns it with its generated id. */
public interface SaveEnfermedadPort {
  EnfermedadModel save(EnfermedadModel enfermedad);
}
