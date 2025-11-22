package devbitsve.infraestructura.rest.recurso;

import devbitsve.aplicacion.servicio.ServicioEmpleado;
import devbitsve.dominio.modelo.Empleado;
import devbitsve.dominio.modelo.EstadoEmpleado;
import devbitsve.infraestructura.rest.dto.*;
import devbitsve.infraestructura.rest.mapper.EmpleadoMapper;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.List;

/**
 * Recurso REST para gestión de empleados
 */
@Path("/api/empleados")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Empleados", description = "Operaciones de gestión de empleados")
public class EmpleadoRecurso {

    @Inject
    ServicioEmpleado servicioEmpleado;

    @Inject
    EmpleadoMapper mapper;

    @POST
    @Operation(summary = "Crear un nuevo empleado", description = "Crea un nuevo empleado en el sistema")
    @APIResponse(responseCode = "201", description = "Empleado creado exitosamente", content = @Content(schema = @Schema(implementation = EmpleadoResponse.class)))
    @APIResponse(responseCode = "400", description = "Datos inválidos o empleado ya existe")
    public Response crearEmpleado(CrearEmpleadoRequest request) {
        try {
            Empleado empleado = servicioEmpleado.crearEmpleado(
                    mapper.toInformacionPersonal(request.informacionPersonal),
                    mapper.toInformacionContacto(request.informacionContacto),
                    mapper.toInformacionLaboral(request.cargoInicial));

            EmpleadoResponse response = mapper.toEmpleadoResponse(empleado);
            return Response.status(Response.Status.CREATED).entity(response).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorResponse(e.getMessage()))
                    .build();
        }
    }

    @GET
    @Operation(summary = "Obtener todos los empleados", description = "Retorna la lista completa de empleados")
    @APIResponse(responseCode = "200", description = "Lista de empleados obtenida exitosamente", content = @Content(schema = @Schema(implementation = EmpleadoResponse.class)))
    public Response obtenerTodosLosEmpleados() {
        List<Empleado> empleados = servicioEmpleado.obtenerTodosLosEmpleados();
        List<EmpleadoResponse> response = mapper.toEmpleadoResponseList(empleados);
        return Response.ok(response).build();
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Obtener empleado por ID", description = "Busca un empleado por su identificador único")
    @APIResponse(responseCode = "200", description = "Empleado encontrado", content = @Content(schema = @Schema(implementation = EmpleadoResponse.class)))
    @APIResponse(responseCode = "404", description = "Empleado no encontrado")
    public Response obtenerEmpleadoPorId(
            @Parameter(description = "ID del empleado", required = true) @PathParam("id") String id) {
        return servicioEmpleado.buscarEmpleadoPorId(id)
                .map(mapper::toEmpleadoResponse)
                .map(Response::ok)
                .orElse(Response.status(Response.Status.NOT_FOUND))
                .build();
    }

    @GET
    @Path("/documento/{documentoIdentidad}")
    @Operation(summary = "Buscar empleado por documento", description = "Busca un empleado por su documento de identidad")
    @APIResponse(responseCode = "200", description = "Empleado encontrado", content = @Content(schema = @Schema(implementation = EmpleadoResponse.class)))
    @APIResponse(responseCode = "404", description = "Empleado no encontrado")
    public Response buscarPorDocumento(
            @Parameter(description = "Documento de identidad", required = true) @PathParam("documentoIdentidad") String documentoIdentidad) {
        return servicioEmpleado.buscarEmpleadoPorDocumento(documentoIdentidad)
                .map(mapper::toEmpleadoResponse)
                .map(Response::ok)
                .orElse(Response.status(Response.Status.NOT_FOUND))
                .build();
    }

    @GET
    @Path("/estado/{estado}")
    @Operation(summary = "Obtener empleados por estado", description = "Filtra empleados por su estado actual")
    @APIResponse(responseCode = "200", description = "Lista de empleados filtrada por estado", content = @Content(schema = @Schema(implementation = EmpleadoResponse.class)))
    public Response obtenerPorEstado(
            @Parameter(description = "Estado del empleado (ACTIVO, INACTIVO, LICENCIA)", required = true) @PathParam("estado") String estado) {
        try {
            EstadoEmpleado estadoEmpleado = EstadoEmpleado.valueOf(estado.toUpperCase());
            List<Empleado> empleados = servicioEmpleado.obtenerEmpleadosPorEstado(estadoEmpleado);
            List<EmpleadoResponse> response = mapper.toEmpleadoResponseList(empleados);
            return Response.ok(response).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorResponse("Estado inválido: " + estado))
                    .build();
        }
    }

    @GET
    @Path("/departamento/{departamento}")
    @Operation(summary = "Obtener empleados por departamento", description = "Filtra empleados por departamento")
    @APIResponse(responseCode = "200", description = "Lista de empleados filtrada por departamento", content = @Content(schema = @Schema(implementation = EmpleadoResponse.class)))
    public Response obtenerPorDepartamento(
            @Parameter(description = "Nombre del departamento", required = true) @PathParam("departamento") String departamento) {
        List<Empleado> empleados = servicioEmpleado.obtenerEmpleadosPorDepartamento(departamento);
        List<EmpleadoResponse> response = mapper.toEmpleadoResponseList(empleados);
        return Response.ok(response).build();
    }

    @PUT
    @Path("/{id}/informacion-personal")
    @Operation(summary = "Actualizar información personal", description = "Actualiza la información personal de un empleado")
    @APIResponse(responseCode = "200", description = "Información actualizada exitosamente", content = @Content(schema = @Schema(implementation = EmpleadoResponse.class)))
    @APIResponse(responseCode = "404", description = "Empleado no encontrado")
    public Response actualizarInformacionPersonal(
            @Parameter(description = "ID del empleado", required = true) @PathParam("id") String id,
            InformacionPersonalDTO dto) {
        try {
            Empleado empleado = servicioEmpleado.actualizarInformacionPersonal(
                    id,
                    mapper.toInformacionPersonal(dto));
            EmpleadoResponse response = mapper.toEmpleadoResponse(empleado);
            return Response.ok(response).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(new ErrorResponse(e.getMessage()))
                    .build();
        }
    }

    @PUT
    @Path("/{id}/informacion-contacto")
    @Operation(summary = "Actualizar información de contacto", description = "Actualiza la información de contacto de un empleado")
    @APIResponse(responseCode = "200", description = "Información actualizada exitosamente", content = @Content(schema = @Schema(implementation = EmpleadoResponse.class)))
    @APIResponse(responseCode = "404", description = "Empleado no encontrado")
    public Response actualizarInformacionContacto(
            @Parameter(description = "ID del empleado", required = true) @PathParam("id") String id,
            InformacionContactoDTO dto) {
        try {
            Empleado empleado = servicioEmpleado.actualizarInformacionContacto(
                    id,
                    mapper.toInformacionContacto(dto));
            EmpleadoResponse response = mapper.toEmpleadoResponse(empleado);
            return Response.ok(response).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(new ErrorResponse(e.getMessage()))
                    .build();
        }
    }

    @PUT
    @Path("/{id}/cambiar-cargo")
    @Operation(summary = "Cambiar cargo del empleado", description = "Cambia el cargo actual del empleado y registra el cambio en el historial")
    @APIResponse(responseCode = "200", description = "Cargo cambiado exitosamente", content = @Content(schema = @Schema(implementation = EmpleadoResponse.class)))
    @APIResponse(responseCode = "404", description = "Empleado no encontrado")
    public Response cambiarCargo(
            @Parameter(description = "ID del empleado", required = true) @PathParam("id") String id,
            InformacionLaboralDTO dto) {
        try {
            Empleado empleado = servicioEmpleado.cambiarCargo(
                    id,
                    mapper.toInformacionLaboral(dto));
            EmpleadoResponse response = mapper.toEmpleadoResponse(empleado);
            return Response.ok(response).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(new ErrorResponse(e.getMessage()))
                    .build();
        }
    }

    @PUT
    @Path("/{id}/estado/{estado}")
    @Operation(summary = "Cambiar estado del empleado", description = "Cambia el estado del empleado")
    @APIResponse(responseCode = "200", description = "Estado cambiado exitosamente", content = @Content(schema = @Schema(implementation = EmpleadoResponse.class)))
    @APIResponse(responseCode = "404", description = "Empleado no encontrado")
    @APIResponse(responseCode = "400", description = "Estado inválido")
    public Response cambiarEstado(
            @Parameter(description = "ID del empleado", required = true) @PathParam("id") String id,
            @Parameter(description = "Nuevo estado (ACTIVO, INACTIVO, LICENCIA)", required = true) @PathParam("estado") String estado) {
        try {
            EstadoEmpleado nuevoEstado = EstadoEmpleado.valueOf(estado.toUpperCase());
            Empleado empleado = servicioEmpleado.cambiarEstado(id, nuevoEstado);
            EmpleadoResponse response = mapper.toEmpleadoResponse(empleado);
            return Response.ok(response).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorResponse(e.getMessage()))
                    .build();
        }
    }

    @PUT
    @Path("/{id}/desactivar")
    @Operation(summary = "Desactivar empleado", description = "Cambia el estado del empleado a INACTIVO")
    @APIResponse(responseCode = "200", description = "Empleado desactivado exitosamente", content = @Content(schema = @Schema(implementation = EmpleadoResponse.class)))
    @APIResponse(responseCode = "404", description = "Empleado no encontrado")
    public Response desactivar(
            @Parameter(description = "ID del empleado", required = true) @PathParam("id") String id) {
        try {
            Empleado empleado = servicioEmpleado.desactivarEmpleado(id);
            EmpleadoResponse response = mapper.toEmpleadoResponse(empleado);
            return Response.ok(response).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(new ErrorResponse(e.getMessage()))
                    .build();
        }
    }

    @PUT
    @Path("/{id}/reactivar")
    @Operation(summary = "Reactivar empleado", description = "Cambia el estado del empleado a ACTIVO")
    @APIResponse(responseCode = "200", description = "Empleado reactivado exitosamente", content = @Content(schema = @Schema(implementation = EmpleadoResponse.class)))
    @APIResponse(responseCode = "404", description = "Empleado no encontrado")
    public Response reactivar(
            @Parameter(description = "ID del empleado", required = true) @PathParam("id") String id) {
        try {
            Empleado empleado = servicioEmpleado.reactivarEmpleado(id);
            EmpleadoResponse response = mapper.toEmpleadoResponse(empleado);
            return Response.ok(response).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(new ErrorResponse(e.getMessage()))
                    .build();
        }
    }

    @PUT
    @Path("/{id}/licencia")
    @Operation(summary = "Poner empleado en licencia", description = "Cambia el estado del empleado a LICENCIA")
    @APIResponse(responseCode = "200", description = "Empleado puesto en licencia exitosamente", content = @Content(schema = @Schema(implementation = EmpleadoResponse.class)))
    @APIResponse(responseCode = "404", description = "Empleado no encontrado")
    public Response ponerEnLicencia(
            @Parameter(description = "ID del empleado", required = true) @PathParam("id") String id) {
        try {
            Empleado empleado = servicioEmpleado.ponerEmpleadoEnLicencia(id);
            EmpleadoResponse response = mapper.toEmpleadoResponse(empleado);
            return Response.ok(response).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(new ErrorResponse(e.getMessage()))
                    .build();
        }
    }

    @DELETE
    @Path("/{id}")
    @Operation(summary = "Eliminar empleado", description = "Elimina un empleado del sistema")
    @APIResponse(responseCode = "204", description = "Empleado eliminado exitosamente")
    @APIResponse(responseCode = "404", description = "Empleado no encontrado")
    public Response eliminar(
            @Parameter(description = "ID del empleado", required = true) @PathParam("id") String id) {
        boolean eliminado = servicioEmpleado.eliminarEmpleado(id);
        if (eliminado) {
            return Response.noContent().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    /**
     * DTO para respuestas de error
     */
    public static class ErrorResponse {
        public String mensaje;

        public ErrorResponse() {
        }

        public ErrorResponse(String mensaje) {
            this.mensaje = mensaje;
        }
    }
}
