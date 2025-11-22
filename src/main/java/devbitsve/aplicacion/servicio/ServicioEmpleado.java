package devbitsve.aplicacion.servicio;

import devbitsve.dominio.modelo.*;
import devbitsve.dominio.puerto.RepositorioEmpleado;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Servicio de aplicación que orquesta los casos de uso relacionados con
 * empleados.
 * Coordina las operaciones del dominio y la persistencia.
 */
@ApplicationScoped
public class ServicioEmpleado {

    @Inject
    RepositorioEmpleado repositorioEmpleado;

    /**
     * Crea un nuevo empleado en el sistema
     */
    @Transactional
    public Empleado crearEmpleado(
            InformacionPersonal informacionPersonal,
            InformacionContacto informacionContacto,
            InformacionLaboral cargoInicial) {

        // Validar que no exista un empleado con el mismo documento
        if (repositorioEmpleado.existePorDocumentoIdentidad(informacionPersonal.getDocumentoIdentidad())) {
            throw new IllegalArgumentException(
                    "Ya existe un empleado con el documento de identidad: " +
                            informacionPersonal.getDocumentoIdentidad());
        }

        // Crear el empleado
        Empleado empleado = new Empleado(informacionPersonal, informacionContacto, cargoInicial);

        // Persistir
        return repositorioEmpleado.guardar(empleado);
    }

    /**
     * Busca un empleado por su ID
     */
    public Optional<Empleado> buscarEmpleadoPorId(String id) {
        return repositorioEmpleado.buscarPorId(id);
    }

    /**
     * Busca un empleado por su documento de identidad
     */
    public Optional<Empleado> buscarEmpleadoPorDocumento(String documentoIdentidad) {
        return repositorioEmpleado.buscarPorDocumentoIdentidad(documentoIdentidad);
    }

    /**
     * Obtiene todos los empleados
     */
    public List<Empleado> obtenerTodosLosEmpleados() {
        return repositorioEmpleado.obtenerTodos();
    }

    /**
     * Obtiene empleados por estado
     */
    public List<Empleado> obtenerEmpleadosPorEstado(EstadoEmpleado estado) {
        return repositorioEmpleado.buscarPorEstado(estado);
    }

    /**
     * Obtiene empleados por departamento
     */
    public List<Empleado> obtenerEmpleadosPorDepartamento(String departamento) {
        return repositorioEmpleado.buscarPorDepartamento(departamento);
    }

    /**
     * Actualiza la información personal de un empleado
     */
    @Transactional
    public Empleado actualizarInformacionPersonal(String id, InformacionPersonal nuevaInformacion) {
        Empleado empleado = repositorioEmpleado.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Empleado no encontrado con ID: " + id));

        empleado.actualizarInformacionPersonal(nuevaInformacion);
        return repositorioEmpleado.actualizar(empleado);
    }

    /**
     * Actualiza la información de contacto de un empleado
     */
    @Transactional
    public Empleado actualizarInformacionContacto(String id, InformacionContacto nuevaInformacion) {
        Empleado empleado = repositorioEmpleado.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Empleado no encontrado con ID: " + id));

        empleado.actualizarInformacionContacto(nuevaInformacion);
        return repositorioEmpleado.actualizar(empleado);
    }

    /**
     * Cambia el cargo de un empleado
     */
    @Transactional
    public Empleado cambiarCargo(String id, InformacionLaboral nuevoCargo) {
        Empleado empleado = repositorioEmpleado.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Empleado no encontrado con ID: " + id));

        empleado.cambiarCargo(nuevoCargo);
        return repositorioEmpleado.actualizar(empleado);
    }

    /**
     * Cambia el estado de un empleado
     */
    @Transactional
    public Empleado cambiarEstado(String id, EstadoEmpleado nuevoEstado) {
        Empleado empleado = repositorioEmpleado.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Empleado no encontrado con ID: " + id));

        empleado.cambiarEstado(nuevoEstado);
        return repositorioEmpleado.actualizar(empleado);
    }

    /**
     * Desactiva un empleado
     */
    @Transactional
    public Empleado desactivarEmpleado(String id) {
        Empleado empleado = repositorioEmpleado.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Empleado no encontrado con ID: " + id));

        empleado.desactivar();
        return repositorioEmpleado.actualizar(empleado);
    }

    /**
     * Reactiva un empleado
     */
    @Transactional
    public Empleado reactivarEmpleado(String id) {
        Empleado empleado = repositorioEmpleado.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Empleado no encontrado con ID: " + id));

        empleado.reactivar();
        return repositorioEmpleado.actualizar(empleado);
    }

    /**
     * Pone un empleado en licencia
     */
    @Transactional
    public Empleado ponerEmpleadoEnLicencia(String id) {
        Empleado empleado = repositorioEmpleado.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Empleado no encontrado con ID: " + id));

        empleado.ponerEnLicencia();
        return repositorioEmpleado.actualizar(empleado);
    }

    /**
     * Elimina un empleado del sistema
     */
    @Transactional
    public boolean eliminarEmpleado(String id) {
        return repositorioEmpleado.eliminar(id);
    }
}
