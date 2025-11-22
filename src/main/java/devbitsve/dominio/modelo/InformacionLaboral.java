package devbitsve.dominio.modelo;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

/**
 * Value Object que representa la información laboral de un empleado.
 * Incluye cargo, departamento, salario y fechas de vigencia.
 * Es inmutable y puede representar tanto el cargo actual como histórico.
 */
public class InformacionLaboral {

    private final String cargo;
    private final String departamento;
    private final BigDecimal salario;
    private final LocalDate fechaInicio;
    private final LocalDate fechaFin; // null si es el cargo actual

    public InformacionLaboral(
            String cargo,
            String departamento,
            BigDecimal salario,
            LocalDate fechaInicio,
            LocalDate fechaFin) {

        this.cargo = validarCampoRequerido(cargo, "Cargo");
        this.departamento = validarCampoRequerido(departamento, "Departamento");
        this.salario = validarSalario(salario);
        this.fechaInicio = validarFechaInicio(fechaInicio);
        this.fechaFin = fechaFin;

        validarRangoFechas();
    }

    private String validarCampoRequerido(String valor, String nombreCampo) {
        if (valor == null || valor.trim().isEmpty()) {
            throw new IllegalArgumentException(nombreCampo + " es requerido");
        }
        return valor.trim();
    }

    private BigDecimal validarSalario(BigDecimal salario) {
        if (salario == null) {
            throw new IllegalArgumentException("Salario es requerido");
        }
        if (salario.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Salario debe ser mayor a cero");
        }
        return salario;
    }

    private LocalDate validarFechaInicio(LocalDate fechaInicio) {
        if (fechaInicio == null) {
            throw new IllegalArgumentException("Fecha de inicio es requerida");
        }
        return fechaInicio;
    }

    private void validarRangoFechas() {
        if (fechaFin != null && fechaFin.isBefore(fechaInicio)) {
            throw new IllegalArgumentException("Fecha de fin no puede ser anterior a fecha de inicio");
        }
    }

    /**
     * Indica si este cargo está actualmente vigente (no tiene fecha de fin)
     */
    public boolean esCargoActual() {
        return fechaFin == null;
    }

    /**
     * Finaliza el cargo estableciendo la fecha de fin
     */
    public InformacionLaboral finalizarCargo(LocalDate fechaFin) {
        if (fechaFin == null) {
            throw new IllegalArgumentException("Fecha de fin es requerida");
        }
        if (fechaFin.isBefore(this.fechaInicio)) {
            throw new IllegalArgumentException("Fecha de fin no puede ser anterior a fecha de inicio");
        }

        return new InformacionLaboral(
                this.cargo,
                this.departamento,
                this.salario,
                this.fechaInicio,
                fechaFin);
    }

    public String getCargo() {
        return cargo;
    }

    public String getDepartamento() {
        return departamento;
    }

    public BigDecimal getSalario() {
        return salario;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        InformacionLaboral that = (InformacionLaboral) o;
        return Objects.equals(cargo, that.cargo) &&
                Objects.equals(departamento, that.departamento) &&
                Objects.equals(salario, that.salario) &&
                Objects.equals(fechaInicio, that.fechaInicio) &&
                Objects.equals(fechaFin, that.fechaFin);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cargo, departamento, salario, fechaInicio, fechaFin);
    }

    @Override
    public String toString() {
        return "InformacionLaboral{" +
                "cargo='" + cargo + '\'' +
                ", departamento='" + departamento + '\'' +
                ", salario=" + salario +
                ", fechaInicio=" + fechaInicio +
                ", fechaFin=" + fechaFin +
                '}';
    }
}
