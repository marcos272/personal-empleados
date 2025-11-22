package devbitsve.infraestructura.rest.mapper;

import devbitsve.dominio.modelo.*;
import devbitsve.infraestructura.rest.dto.*;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapper para convertir entre entidades de dominio y DTOs
 */
@ApplicationScoped
public class EmpleadoMapper {

    /**
     * Convierte un DTO de información personal a objeto de dominio
     */
    public InformacionPersonal toInformacionPersonal(InformacionPersonalDTO dto) {
        return new InformacionPersonal(
                dto.nombre,
                dto.apellidos,
                dto.documentoIdentidad,
                dto.genero,
                dto.fechaNacimiento);
    }

    /**
     * Convierte un objeto de dominio de información personal a DTO
     */
    public InformacionPersonalDTO toInformacionPersonalDTO(InformacionPersonal informacion) {
        return new InformacionPersonalDTO(
                informacion.getNombre(),
                informacion.getApellidos(),
                informacion.getDocumentoIdentidad(),
                informacion.getGenero(),
                informacion.getFechaNacimiento());
    }

    /**
     * Convierte un DTO de información de contacto a objeto de dominio
     */
    public InformacionContacto toInformacionContacto(InformacionContactoDTO dto) {
        return new InformacionContacto(
                dto.email,
                dto.telefono,
                dto.direccion);
    }

    /**
     * Convierte un objeto de dominio de información de contacto a DTO
     */
    public InformacionContactoDTO toInformacionContactoDTO(InformacionContacto informacion) {
        return new InformacionContactoDTO(
                informacion.getEmail(),
                informacion.getTelefono(),
                informacion.getDireccion());
    }

    /**
     * Convierte un DTO de información laboral a objeto de dominio
     */
    public InformacionLaboral toInformacionLaboral(InformacionLaboralDTO dto) {
        return new InformacionLaboral(
                dto.cargo,
                dto.departamento,
                dto.salario,
                dto.fechaInicio,
                dto.fechaFin);
    }

    /**
     * Convierte un objeto de dominio de información laboral a DTO
     */
    public InformacionLaboralDTO toInformacionLaboralDTO(InformacionLaboral informacion) {
        return new InformacionLaboralDTO(
                informacion.getCargo(),
                informacion.getDepartamento(),
                informacion.getSalario(),
                informacion.getFechaInicio(),
                informacion.getFechaFin());
    }

    /**
     * Convierte una entidad Empleado a DTO de respuesta
     */
    public EmpleadoResponse toEmpleadoResponse(Empleado empleado) {
        List<InformacionLaboralDTO> historial = empleado.getHistorialCargos().stream()
                .map(this::toInformacionLaboralDTO)
                .collect(Collectors.toList());

        return new EmpleadoResponse(
                empleado.getId(),
                toInformacionPersonalDTO(empleado.getInformacionPersonal()),
                toInformacionContactoDTO(empleado.getInformacionContacto()),
                toInformacionLaboralDTO(empleado.getCargoActual()),
                historial,
                empleado.getEstado().name(),
                empleado.getFechaCreacion(),
                empleado.getFechaActualizacion());
    }

    /**
     * Convierte una lista de empleados a lista de DTOs de respuesta
     */
    public List<EmpleadoResponse> toEmpleadoResponseList(List<Empleado> empleados) {
        return empleados.stream()
                .map(this::toEmpleadoResponse)
                .collect(Collectors.toList());
    }
}
