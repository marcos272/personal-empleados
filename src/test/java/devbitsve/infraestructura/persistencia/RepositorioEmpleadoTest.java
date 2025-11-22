package devbitsve.infraestructura.persistencia;

import devbitsve.dominio.modelo.*;
import devbitsve.dominio.puerto.RepositorioEmpleado;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests de integración para el repositorio de empleados con MongoDB.
 */
@QuarkusTest
class RepositorioEmpleadoTest {

    @Inject
    RepositorioEmpleado repositorioEmpleado;

    private Empleado empleadoPrueba;

    @BeforeEach
    void setUp() {
        // Limpiar la base de datos antes de cada test
        repositorioEmpleado.obtenerTodos().forEach(e -> repositorioEmpleado.eliminar(e.getId()));

        // Crear un empleado de prueba
        InformacionPersonal infoPersonal = new InformacionPersonal(
                "Juan",
                "Pérez García",
                "12345678",
                "Masculino",
                LocalDate.of(1990, 5, 15));

        InformacionContacto infoContacto = new InformacionContacto(
                "juan.perez@example.com",
                "+58-412-1234567",
                "Caracas, Venezuela");

        InformacionLaboral cargoInicial = new InformacionLaboral(
                "Desarrollador Senior",
                "Tecnología",
                new BigDecimal("5000.00"),
                LocalDate.of(2020, 1, 15),
                null);

        empleadoPrueba = new Empleado(infoPersonal, infoContacto, cargoInicial);
    }

    @AfterEach
    void tearDown() {
        // Limpiar después de cada test
        repositorioEmpleado.obtenerTodos().forEach(e -> repositorioEmpleado.eliminar(e.getId()));
    }

    @Test
    void deberiaGuardarEmpleado() {
        // When
        Empleado guardado = repositorioEmpleado.guardar(empleadoPrueba);

        // Then
        assertNotNull(guardado.getId());
        assertEquals("Juan", guardado.getInformacionPersonal().getNombre());
        assertEquals("12345678", guardado.getInformacionPersonal().getDocumentoIdentidad());
    }

    @Test
    void deberiaBuscarEmpleadoPorId() {
        // Given
        Empleado guardado = repositorioEmpleado.guardar(empleadoPrueba);

        // When
        Optional<Empleado> encontrado = repositorioEmpleado.buscarPorId(guardado.getId());

        // Then
        assertTrue(encontrado.isPresent());
        assertEquals(guardado.getId(), encontrado.get().getId());
        assertEquals("Juan", encontrado.get().getInformacionPersonal().getNombre());
    }

    @Test
    void deberiaBuscarEmpleadoPorDocumentoIdentidad() {
        // Given
        repositorioEmpleado.guardar(empleadoPrueba);

        // When
        Optional<Empleado> encontrado = repositorioEmpleado.buscarPorDocumentoIdentidad("12345678");

        // Then
        assertTrue(encontrado.isPresent());
        assertEquals("12345678", encontrado.get().getInformacionPersonal().getDocumentoIdentidad());
    }

    @Test
    void deberiaActualizarEmpleado() {
        // Given
        Empleado guardado = repositorioEmpleado.guardar(empleadoPrueba);

        // When
        InformacionContacto nuevoContacto = new InformacionContacto(
                "nuevo.email@example.com",
                "+58-412-9999999",
                "Valencia, Venezuela");
        guardado.actualizarInformacionContacto(nuevoContacto);
        Empleado actualizado = repositorioEmpleado.actualizar(guardado);

        // Then
        assertEquals("nuevo.email@example.com", actualizado.getInformacionContacto().getEmail());
    }

    @Test
    void deberiaBuscarEmpleadosPorEstado() {
        // Given
        repositorioEmpleado.guardar(empleadoPrueba);

        // When
        List<Empleado> activos = repositorioEmpleado.buscarPorEstado(EstadoEmpleado.ACTIVO);

        // Then
        assertEquals(1, activos.size());
        assertEquals(EstadoEmpleado.ACTIVO, activos.get(0).getEstado());
    }

    @Test
    void deberiaBuscarEmpleadosPorDepartamento() {
        // Given
        repositorioEmpleado.guardar(empleadoPrueba);

        // When
        List<Empleado> enTecnologia = repositorioEmpleado.buscarPorDepartamento("Tecnología");

        // Then
        assertEquals(1, enTecnologia.size());
        assertEquals("Tecnología", enTecnologia.get(0).getCargoActual().getDepartamento());
    }

    @Test
    void deberiaEliminarEmpleado() {
        // Given
        Empleado guardado = repositorioEmpleado.guardar(empleadoPrueba);

        // When
        boolean eliminado = repositorioEmpleado.eliminar(guardado.getId());

        // Then
        assertTrue(eliminado);
        assertFalse(repositorioEmpleado.buscarPorId(guardado.getId()).isPresent());
    }

    @Test
    void deberiaVerificarExistenciaPorDocumento() {
        // Given
        repositorioEmpleado.guardar(empleadoPrueba);

        // When
        boolean existe = repositorioEmpleado.existePorDocumentoIdentidad("12345678");
        boolean noExiste = repositorioEmpleado.existePorDocumentoIdentidad("99999999");

        // Then
        assertTrue(existe);
        assertFalse(noExiste);
    }

    @Test
    void deberiaObtenerTodosLosEmpleados() {
        // Given
        repositorioEmpleado.guardar(empleadoPrueba);

        InformacionPersonal otraPersona = new InformacionPersonal(
                "María",
                "González",
                "87654321",
                "Femenino",
                LocalDate.of(1985, 3, 20));

        InformacionContacto otroContacto = new InformacionContacto(
                "maria.gonzalez@example.com",
                "+58-414-7777777",
                "Maracaibo, Venezuela");

        InformacionLaboral otroCargo = new InformacionLaboral(
                "Gerente de Proyectos",
                "PMO",
                new BigDecimal("7000.00"),
                LocalDate.of(2018, 6, 1),
                null);

        Empleado otroEmpleado = new Empleado(otraPersona, otroContacto, otroCargo);
        repositorioEmpleado.guardar(otroEmpleado);

        // When
        List<Empleado> todos = repositorioEmpleado.obtenerTodos();

        // Then
        assertEquals(2, todos.size());
    }
}
