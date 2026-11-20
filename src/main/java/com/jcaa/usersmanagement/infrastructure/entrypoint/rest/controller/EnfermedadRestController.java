package com.jcaa.usersmanagement.infrastructure.entrypoint.rest.controller;

import com.jcaa.usersmanagement.application.port.in.CreateEnfermedadUseCase;
import com.jcaa.usersmanagement.application.port.in.DeleteEnfermedadUseCase;
import com.jcaa.usersmanagement.application.port.in.GetAllEnfermedadesUseCase;
import com.jcaa.usersmanagement.application.port.in.GetEnfermedadByIdUseCase;
import com.jcaa.usersmanagement.application.port.in.GetEnfermedadByNombreUseCase;
import com.jcaa.usersmanagement.application.port.in.UpdateEnfermedadUseCase;
import com.jcaa.usersmanagement.domain.model.EnfermedadModel;
import com.jcaa.usersmanagement.infrastructure.entrypoint.rest.dto.request.CreateEnfermedadRestRequest;
import com.jcaa.usersmanagement.infrastructure.entrypoint.rest.dto.request.UpdateEnfermedadRestRequest;
import com.jcaa.usersmanagement.infrastructure.entrypoint.rest.dto.response.EnfermedadRestResponse;
import com.jcaa.usersmanagement.infrastructure.entrypoint.rest.mapper.EnfermedadRestMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/enfermedades")
@RequiredArgsConstructor
public class EnfermedadRestController implements EnfermedadRestControllerDocs {

    private final CreateEnfermedadUseCase createEnfermedadUseCase;
    private final UpdateEnfermedadUseCase updateEnfermedadUseCase;
    private final DeleteEnfermedadUseCase deleteEnfermedadUseCase;
    private final GetEnfermedadByIdUseCase getEnfermedadByIdUseCase;
    private final GetEnfermedadByNombreUseCase getEnfermedadByNombreUseCase;
    private final GetAllEnfermedadesUseCase getAllEnfermedadesUseCase;

    // ─────────────────────────────────────────────────────────────────────────────
    // POST /api/enfermedades
    // ─────────────────────────────────────────────────────────────────────────────

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EnfermedadRestResponse create(@Valid @RequestBody final CreateEnfermedadRestRequest request) {
        final EnfermedadModel enfermedad = createEnfermedadUseCase.execute(
                EnfermedadRestMapper.toCreateCommand(request));
        return EnfermedadRestMapper.toResponse(enfermedad);
    }

    // ─────────────────────────────────────────────────────────────────────────────
    // GET /api/enfermedades
    // ─────────────────────────────────────────────────────────────────────────────

    @GetMapping
    public List<EnfermedadRestResponse> getAll() {
        return EnfermedadRestMapper.toResponseList(getAllEnfermedadesUseCase.execute());
    }

    // ─────────────────────────────────────────────────────────────────────────────
    // GET /api/enfermedades/{id}
    // ─────────────────────────────────────────────────────────────────────────────

    @GetMapping("/{id}")
    public EnfermedadRestResponse getById(@PathVariable final Long id) {
        final EnfermedadModel enfermedad = getEnfermedadByIdUseCase.execute(
                EnfermedadRestMapper.toGetByIdQuery(id));
        return EnfermedadRestMapper.toResponse(enfermedad);
    }

    // ─────────────────────────────────────────────────────────────────────────────
    // GET /api/enfermedades/nombre/{nombre}
    // ─────────────────────────────────────────────────────────────────────────────

    @GetMapping("/nombre/{nombre}")
    public EnfermedadRestResponse getByNombre(@PathVariable final String nombre) {
        final EnfermedadModel enfermedad = getEnfermedadByNombreUseCase.execute(
                EnfermedadRestMapper.toGetByNombreQuery(nombre));
        return EnfermedadRestMapper.toResponse(enfermedad);
    }

    // ─────────────────────────────────────────────────────────────────────────────
    // PUT /api/enfermedades/{id}
    // ─────────────────────────────────────────────────────────────────────────────

    @PutMapping("/{id}")
    public EnfermedadRestResponse update(
            @PathVariable final Long id,
            @Valid @RequestBody final UpdateEnfermedadRestRequest request) {
        final EnfermedadModel enfermedad = updateEnfermedadUseCase.execute(
                EnfermedadRestMapper.toUpdateCommand(id, request));
        return EnfermedadRestMapper.toResponse(enfermedad);
    }

    // ─────────────────────────────────────────────────────────────────────────────
    // DELETE /api/enfermedades/{id}
    // ─────────────────────────────────────────────────────────────────────────────

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable final Long id) {
        deleteEnfermedadUseCase.execute(EnfermedadRestMapper.toDeleteCommand(id));
    }
}
