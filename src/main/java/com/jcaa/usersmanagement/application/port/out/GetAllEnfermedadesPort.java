package com.jcaa.usersmanagement.application.port.out;

import com.jcaa.usersmanagement.domain.model.EnfermedadModel;
import java.util.List;

/** Persistence output port: retrieves all enfermedades. */
public interface GetAllEnfermedadesPort {
  List<EnfermedadModel> getAll();
}
