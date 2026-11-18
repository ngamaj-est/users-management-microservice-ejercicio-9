package com.jcaa.usersmanagement.application.port.out;

import com.jcaa.usersmanagement.domain.model.EnfermedadModel;
import com.jcaa.usersmanagement.domain.valueobject.NombreEnfermedad;
import java.util.Optional;

/**
 * Persistence output port: looks up an enfermedad by its unique nombre.
 * Used to enforce the UNIQUE constraint before create/update operations.
 */
public interface GetEnfermedadByNombrePort {
  Optional<EnfermedadModel> getByNombre(NombreEnfermedad nombre);
}
