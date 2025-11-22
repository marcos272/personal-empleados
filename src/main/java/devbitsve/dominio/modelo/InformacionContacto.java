package devbitsve.dominio.modelo;

import java.util.Objects;

/**
 * Value Object que representa la información de contacto de un empleado.
 * Es inmutable y contiene datos de comunicación.
 */
public class InformacionContacto {

    private final String email;
    private final String telefono;
    private final String direccion;

    public InformacionContacto(String email, String telefono, String direccion) {
        this.email = validarEmail(email);
        this.telefono = telefono;
        this.direccion = direccion;
    }

    private String validarEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email es requerido");
        }

        // Validación básica de formato de email
        if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new IllegalArgumentException("Formato de email inválido");
        }

        return email.trim().toLowerCase();
    }

    public String getEmail() {
        return email;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        InformacionContacto that = (InformacionContacto) o;
        return Objects.equals(email, that.email) &&
                Objects.equals(telefono, that.telefono) &&
                Objects.equals(direccion, that.direccion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, telefono, direccion);
    }

    @Override
    public String toString() {
        return "InformacionContacto{" +
                "email='" + email + '\'' +
                ", telefono='" + telefono + '\'' +
                ", direccion='" + direccion + '\'' +
                '}';
    }
}
