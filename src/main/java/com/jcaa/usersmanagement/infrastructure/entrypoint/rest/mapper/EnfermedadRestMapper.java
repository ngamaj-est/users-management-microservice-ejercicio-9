package com.jcaa.usersmanagement.infrastructure.entrypoint.rest.mapper;

import com.jcaa.usersmanagement.application.service.dto.command.CreateEnfermedadCommand;
import com.jcaa.usersmanagement.application.service.dto.command.DeleteEnfermedadCommand;
import com.jcaa.usersmanagement.application.service.dto.command.UpdateEnfermedadCommand;
import com.jcaa.usersmanagement.application.service.dto.query.GetEnfermedadByIdQuery;
import com.jcaa.usersmanagement.application.service.dto.query.GetEnfermedadByNombreQuery;
import com.jcaa.usersmanagement.domain.model.EnfermedadModel;
import com.jcaa.usersmanagement.infrastructure.entrypoint.rest.dto.request.CreateEnfermedadRestRequest;
import com.jcaa.usersmanagement.infrastructure.entrypoint.rest.dto.request.UpdateEnfermedadRestRequest;
import com.jcaa.usersmanagement.infrastructure.entrypoint.rest.dto.response.EnfermedadRestResponse;
import lombok.experimental.UtilityClass;

import java.util.List;

@UtilityClass
public class EnfermedadRestMapper {

    public CreateEnfermedadCommand toCreateCommand(CreateEnfermedadRestRequest request) {
        return new CreateEnfermedadCommand(
                request.nombre(),
                request.nombreCientifico(),
                request.nivelGravedad(),
                request.sintomas(),
                request.medicamentos(),
                request.esContagiosa(),
                request.esCubiertaPorPos(),
                request.requiereIncapacidad()
        );
    }

    public UpdateEnfermedadCommand toUpdateCommand(Long id, UpdateEnfermedadRestRequest request) {
        return new UpdateEnfermedadCommand(
                id,
                request.nombre(),
                request.nombreCientifico(),
                request.nivelGravedad(),
                request.sintomas(),
                request.medicamentos(),
                request.esContagiosa(),
                request.esCubiertaPorPos(),
                request.requiereIncapacidad()
        );
    }

    public GetEnfermedadByIdQuery toGetByIdQuery(Long id) {
        return new GetEnfermedadByIdQuery(id);
    }

    public GetEnfermedadByNombreQuery toGetByNombreQuery(String nombre) {
        return new GetEnfermedadByNombreQuery(nombre);
    }

    public DeleteEnfermedadCommand toDeleteCommand(Long id) {
        return new DeleteEnfermedadCommand(id);
    }

    public EnfermedadRestResponse toResponse(EnfermedadModel model) {
        return new EnfermedadRestResponse(
                model.getId().value(),
                model.getNombre().value(),
                model.getNombreCientifico().value(),
                model.getNivelGravedad().value().name(),
                model.getSintomas().value(),
                model.getMedicamentos().value(),
                model.isEsContagiosa(),
                model.isEsCubiertaPorPos(),
                model.isRequiereIncapacidad()
        );
    }

    public List<EnfermedadRestResponse> toResponseList(List<EnfermedadModel> models) {
        return models.stream().map(EnfermedadRestMapper::toResponse).toList();
    }
}
