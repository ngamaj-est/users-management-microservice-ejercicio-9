package com.jcaa.usersmanagement.application.port.in;

import com.jcaa.usersmanagement.domain.model.EnfermedadModel;
import java.util.List;

public interface GetAllEnfermedadesUseCase {
  List<EnfermedadModel> execute();
}

