package devbitsve.dominio.puerto;

import devbitsve.dominio.modelo.Empleado;
import devbitsve.dominio.modelo.EstadoEmpleado;

import java.util.List;
import java.util.Optional;

/**
 * Puerto (interface) del repositorio de empleados.
 * Define el contrato para la persistencia de empleados sin conocer la
 * implementación.
 * Sigue el principio de inversión de dependencias de la arquitectura hexagonal.
 */
public interface RepositorioEmpleado {

    /**
     * Guarda un nuevo empleado
     * 
     * @param empleado El empleado a guardar
     * @return El empleado guardado con su ID asignado
     */
    Empleado guardar(Empleado empleado);

    /**
     * Actualiza un empleado existente
     * 
     * @param empleado El empleado a actualizar
     * @return El empleado actualizado
     */
    Empleado actualizar(Empleado empleado);

    /**
     * Busca un empleado por su ID
     * 
     * @param id El ID del empleado
     * @return Optional con el empleado si existe, Optional.empty() si no
     */
    Optional<Empleado> buscarPorId(String id);

    /**
     * Busca un empleado por su documento de identidad
     * 
     * @param documentoIdentidad El documento de identidad
     * @return Optional con el empleado si existe, Optional.empty() si no
     */
    Optional<Empleado> buscarPorDocumentoIdentidad(String documentoIdentidad);

    /**
     * Busca empleados por estado
     * 
     * @param estado El estado a buscar
     * @return Lista de empleados con ese estado
     */
    List<Empleado> buscarPorEstado(EstadoEmpleado estado);

    /**
     * Busca empleados por departamento
     * 
     * @param departamento El nombre del departamento
     * @return Lista de empleados en ese departamento
     */
    List<Empleado> buscarPorDepartamento(String departamento);

    /**
     * Obtiene todos los empleados
     * 
     * @return Lista de todos los empleados
     */
    List<Empleado> obtenerTodos();

    /**
     * Elimina un empleado por su ID
     * 
     * @param id El ID del empleado a eliminar
     * @return true si se eliminó, false si no existía
     */
    boolean eliminar(String id);

    /**
     * Verifica si existe un empleado con el documento de identidad dado
     * 
     * @param documentoIdentidad El documento de identidad
     * @return true si existe, false si no
     */
    boolean existePorDocumentoIdentidad(String documentoIdentidad);
}
