package devbitsve.infraestructura.rest.dto;

/**
 * DTO para la solicitud de creación de un empleado
 */
public class CrearEmpleadoRequest {

    public InformacionPersonalDTO informacionPersonal;
    public InformacionContactoDTO informacionContacto;
    public InformacionLaboralDTO cargoInicial;

    public CrearEmpleadoRequest() {
    }

    public CrearEmpleadoRequest(
            InformacionPersonalDTO informacionPersonal,
            InformacionContactoDTO informacionContacto,
            InformacionLaboralDTO cargoInicial) {
        this.informacionPersonal = informacionPersonal;
        this.informacionContacto = informacionContacto;
        this.cargoInicial = cargoInicial;
    }
}
