package devbitsve.dominio.modelo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Entidad raíz del agregado Empleado.
 * Representa un empleado en el sistema con toda su información personal,
 * laboral e historial.
 */
public class Empleado {

    private String id;
    private InformacionPersonal informacionPersonal;
    private InformacionContacto informacionContacto;
    private InformacionLaboral cargoActual;
    private List<InformacionLaboral> historialCargos;
    private EstadoEmpleado estado;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;

    // Constructor para crear un nuevo empleado
    public Empleado(
            InformacionPersonal informacionPersonal,
            InformacionContacto informacionContacto,
            InformacionLaboral cargoActual) {

        this.informacionPersonal = validarRequerido(informacionPersonal, "Información personal");
        this.informacionContacto = validarRequerido(informacionContacto, "Información de contacto");
        this.cargoActual = validarRequerido(cargoActual, "Cargo actual");
        this.historialCargos = new ArrayList<>();
        this.estado = EstadoEmpleado.ACTIVO;
        this.fechaCreacion = LocalDateTime.now();
        this.fechaActualizacion = LocalDateTime.now();
    }

    // Constructor completo (para reconstrucción desde persistencia)
    public Empleado(
            String id,
            InformacionPersonal informacionPersonal,
            InformacionContacto informacionContacto,
            InformacionLaboral cargoActual,
            List<InformacionLaboral> historialCargos,
            EstadoEmpleado estado,
            LocalDateTime fechaCreacion,
            LocalDateTime fechaActualizacion) {

        this.id = id;
        this.informacionPersonal = informacionPersonal;
        this.informacionContacto = informacionContacto;
        this.cargoActual = cargoActual;
        this.historialCargos = historialCargos != null ? new ArrayList<>(historialCargos) : new ArrayList<>();
        this.estado = estado != null ? estado : EstadoEmpleado.ACTIVO;
        this.fechaCreacion = fechaCreacion;
        this.fechaActualizacion = fechaActualizacion;
    }

    private <T> T validarRequerido(T valor, String nombreCampo) {
        if (valor == null) {
            throw new IllegalArgumentException(nombreCampo + " es requerido");
        }
        return valor;
    }

    /**
     * Cambia el cargo del empleado, moviendo el cargo actual al historial
     */
    public void cambiarCargo(InformacionLaboral nuevoCargo) {
        if (nuevoCargo == null) {
            throw new IllegalArgumentException("Nuevo cargo es requerido");
        }

        // Finalizar el cargo actual y moverlo al historial
        if (this.cargoActual != null) {
            InformacionLaboral cargoFinalizado = this.cargoActual.finalizarCargo(
                    nuevoCargo.getFechaInicio().minusDays(1));
            this.historialCargos.add(cargoFinalizado);
        }

        this.cargoActual = nuevoCargo;
        this.fechaActualizacion = LocalDateTime.now();
    }

    /**
     * Actualiza la información personal del empleado
     */
    public void actualizarInformacionPersonal(InformacionPersonal nuevaInformacion) {
        this.informacionPersonal = validarRequerido(nuevaInformacion, "Información personal");
        this.fechaActualizacion = LocalDateTime.now();
    }

    /**
     * Actualiza la información de contacto del empleado
     */
    public void actualizarInformacionContacto(InformacionContacto nuevaInformacion) {
        this.informacionContacto = validarRequerido(nuevaInformacion, "Información de contacto");
        this.fechaActualizacion = LocalDateTime.now();
    }

    /**
     * Cambia el estado del empleado
     */
    public void cambiarEstado(EstadoEmpleado nuevoEstado) {
        if (nuevoEstado == null) {
            throw new IllegalArgumentException("Estado es requerido");
        }
        this.estado = nuevoEstado;
        this.fechaActualizacion = LocalDateTime.now();
    }

    /**
     * Desactiva el empleado
     */
    public void desactivar() {
        cambiarEstado(EstadoEmpleado.INACTIVO);
    }

    /**
     * Reactiva el empleado
     */
    public void reactivar() {
        cambiarEstado(EstadoEmpleado.ACTIVO);
    }

    /**
     * Pone al empleado en licencia
     */
    public void ponerEnLicencia() {
        cambiarEstado(EstadoEmpleado.LICENCIA);
    }

    /**
     * Verifica si el empleado está activo
     */
    public boolean estaActivo() {
        return this.estado == EstadoEmpleado.ACTIVO;
    }

    // Getters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public InformacionPersonal getInformacionPersonal() {
        return informacionPersonal;
    }

    public InformacionContacto getInformacionContacto() {
        return informacionContacto;
    }

    public InformacionLaboral getCargoActual() {
        return cargoActual;
    }

    public List<InformacionLaboral> getHistorialCargos() {
        return Collections.unmodifiableList(historialCargos);
    }

    public EstadoEmpleado getEstado() {
        return estado;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public LocalDateTime getFechaActualizacion() {
        return fechaActualizacion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Empleado empleado = (Empleado) o;
        return Objects.equals(id, empleado.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Empleado{" +
                "id='" + id + '\'' +
                ", informacionPersonal=" + informacionPersonal +
                ", estado=" + estado +
                ", cargoActual=" + cargoActual +
                '}';
    }
}
