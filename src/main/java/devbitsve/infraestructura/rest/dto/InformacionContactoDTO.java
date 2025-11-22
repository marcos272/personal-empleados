package devbitsve.infraestructura.rest.dto;

/**
 * DTO para transferir información de contacto de un empleado
 */
public class InformacionContactoDTO {

    public String email;
    public String telefono;
    public String direccion;

    public InformacionContactoDTO() {
    }

    public InformacionContactoDTO(String email, String telefono, String direccion) {
        this.email = email;
        this.telefono = telefono;
        this.direccion = direccion;
    }
}
