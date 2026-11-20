package com.jcaa.usersmanagement.infrastructure.entrypoint.rest.controller;

import com.jcaa.usersmanagement.infrastructure.config.OpenApiConfig;
import com.jcaa.usersmanagement.infrastructure.entrypoint.rest.dto.request.CreateEnfermedadRestRequest;
import com.jcaa.usersmanagement.infrastructure.entrypoint.rest.dto.request.UpdateEnfermedadRestRequest;
import com.jcaa.usersmanagement.infrastructure.entrypoint.rest.dto.response.ApiErrorResponse;
import com.jcaa.usersmanagement.infrastructure.entrypoint.rest.dto.response.EnfermedadRestResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Contrato OpenAPI / Swagger para {@link EnfermedadRestController}.
 *
 * <p>
 * Toda la documentación de la API (anotaciones {@code @Operation},
 * {@code @ApiResponses}, etc.)
 * vive aquí para mantener el controlador limpio y enfocado únicamente en la
 * lógica de despacho.
 */
@Tag(name = "Enfermedades", description = "Gestión de enfermedades: crear, consultar, actualizar y eliminar.")
public interface EnfermedadRestControllerDocs {

    // ─────────────────────────────────────────────────────────────────────────────
    // POST /api/enfermedades
    // ─────────────────────────────────────────────────────────────────────────────

    @Operation(
            summary = "Crear enfermedad",
            description = "Registra una nueva enfermedad en el sistema. "
                    + "El nombre debe ser único y se validará que el nivel de gravedad sea uno de los valores permitidos: "
                    + "LEVE, MODERADA, GRAVE o CRITICA.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Enfermedad creada exitosamente.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = EnfermedadRestResponse.class))),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos (campos requeridos vacíos, formato incorrecto o nivel de gravedad no reconocido).",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "409", description = "Ya existe una enfermedad registrada con ese nombre.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    EnfermedadRestResponse create(@Valid @RequestBody CreateEnfermedadRestRequest request);

    // ─────────────────────────────────────────────────────────────────────────────
    // GET /api/enfermedades
    // ─────────────────────────────────────────────────────────────────────────────

    @SecurityRequirement(name = OpenApiConfig.BEARER_AUTH)
    @Operation(
            summary = "Listar todas las enfermedades",
            description = "Retorna la lista completa de enfermedades registradas en el sistema.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de enfermedades obtenida exitosamente. Puede ser vacía.",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = EnfermedadRestResponse.class)))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    List<EnfermedadRestResponse> getAll();

    // ─────────────────────────────────────────────────────────────────────────────
    // GET /api/enfermedades/{id}
    // ─────────────────────────────────────────────────────────────────────────────

    @SecurityRequirement(name = OpenApiConfig.BEARER_AUTH)
    @Operation(
            summary = "Obtener enfermedad por ID",
            description = "Retorna los datos de una enfermedad específica identificada por su ID numérico.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Enfermedad encontrada.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = EnfermedadRestResponse.class))),
            @ApiResponse(responseCode = "404", description = "No existe ninguna enfermedad con el ID proporcionado.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    EnfermedadRestResponse getById(
            @Parameter(description = "ID numérico único de la enfermedad.", example = "1", required = true)
            @PathVariable Long id);

    // ─────────────────────────────────────────────────────────────────────────────
    // GET /api/enfermedades/nombre/{nombre}
    // ─────────────────────────────────────────────────────────────────────────────

    @SecurityRequirement(name = OpenApiConfig.BEARER_AUTH)
    @Operation(
            summary = "Obtener enfermedad por nombre",
            description = "Retorna los datos de una enfermedad identificada por su nombre exacto.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Enfermedad encontrada.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = EnfermedadRestResponse.class))),
            @ApiResponse(responseCode = "404", description = "No existe ninguna enfermedad con el nombre proporcionado.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    EnfermedadRestResponse getByNombre(
            @Parameter(description = "Nombre de la enfermedad.", example = "Dengue", required = true)
            @PathVariable String nombre);

    // ─────────────────────────────────────────────────────────────────────────────
    // PUT /api/enfermedades/{id}
    // ─────────────────────────────────────────────────────────────────────────────

    @SecurityRequirement(name = OpenApiConfig.BEARER_AUTH)
    @Operation(
            summary = "Actualizar enfermedad",
            description = "Actualiza los datos de una enfermedad existente identificada por su ID. "
                    + "Si se cambia el nombre, este no debe estar en uso por otra enfermedad.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Enfermedad actualizada exitosamente.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = EnfermedadRestResponse.class))),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "No existe ninguna enfermedad con el ID proporcionado.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "409", description = "El nuevo nombre ya está en uso por otra enfermedad.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    EnfermedadRestResponse update(
            @Parameter(description = "ID numérico único de la enfermedad a actualizar.", example = "1", required = true)
            @PathVariable Long id,
            @Valid @RequestBody UpdateEnfermedadRestRequest request);

    // ─────────────────────────────────────────────────────────────────────────────
    // DELETE /api/enfermedades/{id}
    // ─────────────────────────────────────────────────────────────────────────────

    @SecurityRequirement(name = OpenApiConfig.BEARER_AUTH)
    @Operation(
            summary = "Eliminar enfermedad",
            description = "Elimina permanentemente una enfermedad del sistema por su ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Enfermedad eliminada exitosamente."),
            @ApiResponse(responseCode = "404", description = "No existe ninguna enfermedad con el ID proporcionado.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    void delete(
            @Parameter(description = "ID numérico único de la enfermedad a eliminar.", example = "1", required = true)
            @PathVariable Long id);
}
