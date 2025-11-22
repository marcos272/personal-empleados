package devbitsve.infraestructura.persistencia.repositorio;

import devbitsve.dominio.modelo.Empleado;
import devbitsve.dominio.modelo.EstadoEmpleado;
import devbitsve.dominio.puerto.RepositorioEmpleado;
import devbitsve.infraestructura.persistencia.entidad.EmpleadoEntidad;
import devbitsve.infraestructura.persistencia.mapper.EmpleadoMapper;
import io.quarkus.mongodb.panache.PanacheMongoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implementación del repositorio de empleados usando MongoDB Panache.
 * Adaptador que implementa el puerto RepositorioEmpleado.
 */
@ApplicationScoped
public class RepositorioEmpleadoMongo implements RepositorioEmpleado, PanacheMongoRepository<EmpleadoEntidad> {

    @Override
    public Empleado guardar(Empleado empleado) {
        EmpleadoEntidad entidad = EmpleadoMapper.toEntidad(empleado);
        persist(entidad);
        return EmpleadoMapper.toDominio(entidad);
    }

    @Override
    public Empleado actualizar(Empleado empleado) {
        if (empleado.getId() == null) {
            throw new IllegalArgumentException("El empleado debe tener un ID para actualizar");
        }

        EmpleadoEntidad entidad = EmpleadoMapper.toEntidad(empleado);
        update(entidad);
        return EmpleadoMapper.toDominio(entidad);
    }

    @Override
    public Optional<Empleado> buscarPorId(String id) {
        try {
            ObjectId objectId = new ObjectId(id);
            return findByIdOptional(objectId)
                    .map(EmpleadoMapper::toDominio);
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Empleado> buscarPorDocumentoIdentidad(String documentoIdentidad) {
        return find("documentoIdentidad", documentoIdentidad)
                .firstResultOptional()
                .map(EmpleadoMapper::toDominio);
    }

    @Override
    public List<Empleado> buscarPorEstado(EstadoEmpleado estado) {
        return find("estado", estado.name())
                .stream()
                .map(EmpleadoMapper::toDominio)
                .collect(Collectors.toList());
    }

    @Override
    public List<Empleado> buscarPorDepartamento(String departamento) {
        return find("cargoActual.departamento", departamento)
                .stream()
                .map(EmpleadoMapper::toDominio)
                .collect(Collectors.toList());
    }

    @Override
    public List<Empleado> obtenerTodos() {
        return findAll()
                .stream()
                .map(EmpleadoMapper::toDominio)
                .collect(Collectors.toList());
    }

    @Override
    public boolean eliminar(String id) {
        try {
            ObjectId objectId = new ObjectId(id);
            return deleteById(objectId);
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    @Override
    public boolean existePorDocumentoIdentidad(String documentoIdentidad) {
        return count("documentoIdentidad", documentoIdentidad) > 0;
    }
}
