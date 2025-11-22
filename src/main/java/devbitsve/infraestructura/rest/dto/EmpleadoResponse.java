package devbitsve.infraestructura.rest.dto;

import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO para la respuesta con información completa de un empleado
 */
public class EmpleadoResponse {

    public String id;
    public InformacionPersonalDTO informacionPersonal;
    public InformacionContactoDTO informacionContacto;
    public InformacionLaboralDTO cargoActual;
    public List<InformacionLaboralDTO> historialCargos;
    public String estado;
    public LocalDateTime fechaCreacion;
    public LocalDateTime fechaActualizacion;

    public EmpleadoResponse() {
    }

    public EmpleadoResponse(
            String id,
            InformacionPersonalDTO informacionPersonal,
            InformacionContactoDTO informacionContacto,
            InformacionLaboralDTO cargoActual,
            List<InformacionLaboralDTO> historialCargos,
            String estado,
            LocalDateTime fechaCreacion,
            LocalDateTime fechaActualizacion) {
        this.id = id;
        this.informacionPersonal = informacionPersonal;
        this.informacionContacto = informacionContacto;
        this.cargoActual = cargoActual;
        this.historialCargos = historialCargos;
        this.estado = estado;
        this.fechaCreacion = fechaCreacion;
        this.fechaActualizacion = fechaActualizacion;
    }
}
