package devbitsve.dominio.modelo;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Value Object que representa la información personal de un empleado.
 * Es inmutable y contiene datos básicos de identificación.
 */
public class InformacionPersonal {

    private final String nombre;
    private final String apellidos;
    private final String documentoIdentidad;
    private final String genero;
    private final LocalDate fechaNacimiento;

    public InformacionPersonal(
            String nombre,
            String apellidos,
            String documentoIdentidad,
            String genero,
            LocalDate fechaNacimiento) {

        this.nombre = validarCampoRequerido(nombre, "Nombre");
        this.apellidos = validarCampoRequerido(apellidos, "Apellidos");
        this.documentoIdentidad = validarCampoRequerido(documentoIdentidad, "Documento de identidad");
        this.genero = genero;
        this.fechaNacimiento = fechaNacimiento;
    }

    private String validarCampoRequerido(String valor, String nombreCampo) {
        if (valor == null || valor.trim().isEmpty()) {
            throw new IllegalArgumentException(nombreCampo + " es requerido");
        }
        return valor.trim();
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public String getDocumentoIdentidad() {
        return documentoIdentidad;
    }

    public String getGenero() {
        return genero;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public String getNombreCompleto() {
        return nombre + " " + apellidos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        InformacionPersonal that = (InformacionPersonal) o;
        return Objects.equals(documentoIdentidad, that.documentoIdentidad);
    }

    @Override
    public int hashCode() {
        return Objects.hash(documentoIdentidad);
    }

    @Override
    public String toString() {
        return "InformacionPersonal{" +
                "nombre='" + nombre + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", documentoIdentidad='" + documentoIdentidad + '\'' +
                ", genero='" + genero + '\'' +
                ", fechaNacimiento=" + fechaNacimiento +
                '}';
    }
}
