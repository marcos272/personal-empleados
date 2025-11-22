package devbitsve.dominio.modelo;

/**
 * Enum que representa los posibles estados de un empleado.
 */
public enum EstadoEmpleado {
    /**
     * Empleado activo y trabajando normalmente
     */
    ACTIVO,

    /**
     * Empleado inactivo (despedido, renunciado, etc.)
     */
    INACTIVO,

    /**
     * Empleado en licencia temporal (médica, vacaciones, etc.)
     */
    LICENCIA
}
