package com.jcaa.usersmanagement.application.service;

import com.jcaa.usersmanagement.application.port.in.GetAllEnfermedadesUseCase;
import com.jcaa.usersmanagement.application.port.out.GetAllEnfermedadesPort;
import com.jcaa.usersmanagement.domain.model.EnfermedadModel;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetAllEnfermedadesService implements GetAllEnfermedadesUseCase {

  private final GetAllEnfermedadesPort getAllEnfermedadesPort;

  @Override
  public List<EnfermedadModel> execute() {
    return getAllEnfermedadesPort.getAll();
  }
}
