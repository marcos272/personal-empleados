package devbitsve.infraestructura.persistencia.mapper;

import devbitsve.dominio.modelo.*;
import devbitsve.infraestructura.persistencia.entidad.EmpleadoEntidad;
import devbitsve.infraestructura.persistencia.entidad.EmpleadoEntidad.InformacionLaboralEntidad;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapper que convierte entre entidades de dominio y entidades de persistencia.
 * Separa las preocupaciones del dominio y la infraestructura.
 */
public class EmpleadoMapper {

    /**
     * Convierte de entidad de dominio a entidad de MongoDB
     */
    public static EmpleadoEntidad toEntidad(Empleado empleado) {
        if (empleado == null) {
            return null;
        }

        EmpleadoEntidad entidad = new EmpleadoEntidad();

        // ID
        if (empleado.getId() != null) {
            entidad.id = new org.bson.types.ObjectId(empleado.getId());
        }

        // Información Personal
        InformacionPersonal infoPersonal = empleado.getInformacionPersonal();
        entidad.nombre = infoPersonal.getNombre();
        entidad.apellidos = infoPersonal.getApellidos();
        entidad.documentoIdentidad = infoPersonal.getDocumentoIdentidad();
        entidad.genero = infoPersonal.getGenero();
        entidad.fechaNacimiento = infoPersonal.getFechaNacimiento();

        // Información de Contacto
        InformacionContacto infoContacto = empleado.getInformacionContacto();
        entidad.email = infoContacto.getEmail();
        entidad.telefono = infoContacto.getTelefono();
        entidad.direccion = infoContacto.getDireccion();

        // Cargo Actual
        entidad.cargoActual = toInformacionLaboralEntidad(empleado.getCargoActual());

        // Historial de Cargos
        entidad.historialCargos = empleado.getHistorialCargos().stream()
                .map(EmpleadoMapper::toInformacionLaboralEntidad)
                .collect(Collectors.toList());

        // Estado
        entidad.estado = empleado.getEstado().name();

        // Auditoría
        entidad.fechaCreacion = empleado.getFechaCreacion();
        entidad.fechaActualizacion = empleado.getFechaActualizacion();

        return entidad;
    }

    /**
     * Convierte de entidad de MongoDB a entidad de dominio
     */
    public static Empleado toDominio(EmpleadoEntidad entidad) {
        if (entidad == null) {
            return null;
        }

        // Información Personal
        InformacionPersonal infoPersonal = new InformacionPersonal(
                entidad.nombre,
                entidad.apellidos,
                entidad.documentoIdentidad,
                entidad.genero,
                entidad.fechaNacimiento);

        // Información de Contacto
        InformacionContacto infoContacto = new InformacionContacto(
                entidad.email,
                entidad.telefono,
                entidad.direccion);

        // Cargo Actual
        InformacionLaboral cargoActual = toInformacionLaboral(entidad.cargoActual);

        // Historial de Cargos
        List<InformacionLaboral> historialCargos = entidad.historialCargos.stream()
                .map(EmpleadoMapper::toInformacionLaboral)
                .collect(Collectors.toList());

        // Estado
        EstadoEmpleado estado = EstadoEmpleado.valueOf(entidad.estado);

        // Crear empleado
        Empleado empleado = new Empleado(
                entidad.id != null ? entidad.id.toHexString() : null,
                infoPersonal,
                infoContacto,
                cargoActual,
                historialCargos,
                estado,
                entidad.fechaCreacion,
                entidad.fechaActualizacion);

        return empleado;
    }

    /**
     * Convierte InformacionLaboral de dominio a entidad
     */
    private static InformacionLaboralEntidad toInformacionLaboralEntidad(InformacionLaboral info) {
        if (info == null) {
            return null;
        }

        return new InformacionLaboralEntidad(
                info.getCargo(),
                info.getDepartamento(),
                info.getSalario(),
                info.getFechaInicio(),
                info.getFechaFin());
    }

    /**
     * Convierte InformacionLaboralEntidad a dominio
     */
    private static InformacionLaboral toInformacionLaboral(InformacionLaboralEntidad entidad) {
        if (entidad == null) {
            return null;
        }

        return new InformacionLaboral(
                entidad.cargo,
                entidad.departamento,
                entidad.salario,
                entidad.fechaInicio,
                entidad.fechaFin);
    }
}
