package devbitsve.infraestructura.rest.dto;

import java.time.LocalDate;

/**
 * DTO para transferir información personal de un empleado
 */
public class InformacionPersonalDTO {

    public String nombre;
    public String apellidos;
    public String documentoIdentidad;
    public String genero;
    public LocalDate fechaNacimiento;

    public InformacionPersonalDTO() {
    }

    public InformacionPersonalDTO(
            String nombre,
            String apellidos,
            String documentoIdentidad,
            String genero,
            LocalDate fechaNacimiento) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.documentoIdentidad = documentoIdentidad;
        this.genero = genero;
        this.fechaNacimiento = fechaNacimiento;
    }
}
