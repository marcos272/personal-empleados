package devbitsve.infraestructura.persistencia.entidad;

import io.quarkus.mongodb.panache.common.MongoEntity;
import org.bson.types.ObjectId;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Entidad de MongoDB que representa un empleado.
 * Usa Panache para simplificar la persistencia.
 */
@MongoEntity(collection = "empleados")
public class EmpleadoEntidad {

    public ObjectId id;

    // Información Personal
    public String nombre;
    public String apellidos;
    public String documentoIdentidad;
    public String genero;
    public LocalDate fechaNacimiento;

    // Información de Contacto
    public String email;
    public String telefono;
    public String direccion;

    // Cargo Actual
    public InformacionLaboralEntidad cargoActual;

    // Historial de Cargos
    public List<InformacionLaboralEntidad> historialCargos = new ArrayList<>();

    // Estado
    public String estado;

    // Auditoría
    public LocalDateTime fechaCreacion;
    public LocalDateTime fechaActualizacion;

    /**
     * Clase embebida que representa la información laboral en MongoDB
     */
    public static class InformacionLaboralEntidad {
        public String cargo;
        public String departamento;
        public BigDecimal salario;
        public LocalDate fechaInicio;
        public LocalDate fechaFin;

        public InformacionLaboralEntidad() {
        }

        public InformacionLaboralEntidad(
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
}
