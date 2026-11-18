package com.jcaa.usersmanagement.application.service.mapper;

import com.jcaa.usersmanagement.application.service.dto.command.CreateEnfermedadCommand;
import com.jcaa.usersmanagement.application.service.dto.command.DeleteEnfermedadCommand;
import com.jcaa.usersmanagement.application.service.dto.command.UpdateEnfermedadCommand;
import com.jcaa.usersmanagement.application.service.dto.query.GetEnfermedadByIdQuery;
import com.jcaa.usersmanagement.domain.model.EnfermedadModel;
import com.jcaa.usersmanagement.domain.valueobject.EnfermedadId;
import com.jcaa.usersmanagement.domain.valueobject.Medicamentos;
import com.jcaa.usersmanagement.domain.valueobject.NivelGravedadVO;
import com.jcaa.usersmanagement.domain.valueobject.NombreCientifico;
import com.jcaa.usersmanagement.domain.valueobject.NombreEnfermedad;
import com.jcaa.usersmanagement.domain.valueobject.Sintomas;
import lombok.experimental.UtilityClass;

@UtilityClass
public class EnfermedadApplicationMapper {

  public EnfermedadModel fromCreateCommandToModel(final CreateEnfermedadCommand command) {
    return EnfermedadModel.create(
        new NombreEnfermedad(command.nombre()),
        new NombreCientifico(command.nombreCientifico()),
        NivelGravedadVO.fromString(command.nivelGravedad()),
        new Sintomas(command.sintomas()),
        new Medicamentos(command.medicamentos()),
        command.esContagiosa(),
        command.esCubiertaPorPos(),
        command.requiereIncapacidad());
  }

  public EnfermedadModel fromUpdateCommandToModel(final UpdateEnfermedadCommand command) {
    return EnfermedadModel.builder()
        .id(new EnfermedadId(command.id()))
        .nombre(new NombreEnfermedad(command.nombre()))
        .nombreCientifico(new NombreCientifico(command.nombreCientifico()))
        .nivelGravedad(NivelGravedadVO.fromString(command.nivelGravedad()))
        .sintomas(new Sintomas(command.sintomas()))
        .medicamentos(new Medicamentos(command.medicamentos()))
        .esContagiosa(command.esContagiosa())
        .esCubiertaPorPos(command.esCubiertaPorPos())
        .requiereIncapacidad(command.requiereIncapacidad())
        .build();
  }

  public EnfermedadId fromDeleteCommandToEnfermedadId(final DeleteEnfermedadCommand command) {
    return new EnfermedadId(command.id());
  }

  public EnfermedadId fromQueryToEnfermedadId(final GetEnfermedadByIdQuery query) {
    return new EnfermedadId(query.id());
  }
}
