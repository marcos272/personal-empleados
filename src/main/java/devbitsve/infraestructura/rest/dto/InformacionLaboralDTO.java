package devbitsve.infraestructura.rest.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * DTO para transferir información laboral de un empleado
 */
public class InformacionLaboralDTO {

    public String cargo;
    public String departamento;
    public BigDecimal salario;
    public LocalDate fechaInicio;
    public LocalDate fechaFin;

    public InformacionLaboralDTO() {
    }

    public InformacionLaboralDTO(
            String cargo,
            String departamento,
            BigDecimal salario,
            LocalDate fechaInicio,
            LocalDate fechaFin) {
        this.cargo = cargo;
        this.departamento = departamento;
        this.salario = salario;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }
}
