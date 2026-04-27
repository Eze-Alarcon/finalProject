package org.ezequiel.proyectofinal.features.hr.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.ezequiel.proyectofinal.features.hr.dto.EmployeeRequestDTO;
import org.ezequiel.proyectofinal.features.hr.dto.EmployeeResponseDTO;
import org.ezequiel.proyectofinal.features.hr.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/employees")
@RequiredArgsConstructor
@Tag(name = "HR - Employees", description = "API de gestión de empleados del sistema. Permite crear, consultar, actualizar y eliminar empleados, priorizando información personal, rol interno y relación jerárquica con su supervisor.")
public class EmployeeController {

    private final EmployeeService employeeService;

        @Operation(
            summary = "Obtener todos los empleados",
            description = "Recupera la lista completa de empleados registrados en el sistema. Incluye datos " +
                "personales básicos, puesto, información de contacto y relación jerárquica si aplica. " +
                "Accesible para roles administrativos y de consulta.",
            security = @SecurityRequirement(name = "bearer")
        )
        @ApiResponses({
            @ApiResponse(
                responseCode = "200",
                description = "Lista de empleados recuperada exitosamente",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = EmployeeResponseDTO.class)
                )
            ),
            @ApiResponse(
                responseCode = "400",
                description = "Datos de entrada inválidos",
                content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                responseCode = "401",
                description = "Token de autenticación no válido o ausente",
                content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                responseCode = "403",
                description = "Sin permisos suficientes - Se requiere rol ADMIN, GESTOR o CONSULTA",
                content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                responseCode = "500",
                description = "Error interno del servidor",
                content = @Content(mediaType = "application/json")
            )
        })
        @GetMapping
        @PreAuthorize("hasAnyRole('ADMIN', 'GESTOR', 'CONSULTA')")
        public ResponseEntity<List<EmployeeResponseDTO>> findAll() {
        return ResponseEntity.ok(employeeService.findAll());
        }

        @Operation(
            summary = "Obtener empleado por ID",
            description = "Recupera la información detallada de un empleado específico mediante su ID único. " +
                "Incluye datos personales, puesto, fechas relevantes, contacto y referencia al supervisor cuando exista. " +
                "Accesible para roles con permisos de consulta.",
            security = @SecurityRequirement(name = "bearer")
        )
        @ApiResponses({
            @ApiResponse(
                responseCode = "200",
                description = "Empleado encontrado y datos recuperados exitosamente",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = EmployeeResponseDTO.class)
                )
            ),
            @ApiResponse(
                responseCode = "400",
                description = "Datos de entrada inválidos",
                content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                responseCode = "401",
                description = "Token de autenticación no válido o ausente",
                content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                responseCode = "403",
                description = "Sin permisos suficientes - Se requiere rol ADMIN, GESTOR o CONSULTA",
                content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                responseCode = "404",
                description = "Empleado no encontrado",
                content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                responseCode = "500",
                description = "Error interno del servidor",
                content = @Content(mediaType = "application/json")
            )
        })
        @GetMapping("/{id}")
        @PreAuthorize("hasAnyRole('ADMIN', 'GESTOR', 'CONSULTA')")
        public ResponseEntity<EmployeeResponseDTO> findById(
            @Parameter(
                description = "ID único del empleado a consultar",
                required = true,
                example = "5"
            )
            @PathVariable Short id) {
        return ResponseEntity.ok(employeeService.findById(id));
        }

        @Operation(
            summary = "Crear nuevo empleado",
            description = "Registra un nuevo empleado en el sistema. Solo usuarios con permisos administrativos " +
                "(ADMIN) o de gestión (GESTOR) pueden crear empleados. El empleado se almacena con su " +
                "información personal, datos de contacto y relación jerárquica si aplica.",
            security = @SecurityRequirement(name = "bearer")
        )
        @ApiResponses({
            @ApiResponse(
                responseCode = "201",
                description = "Empleado creado exitosamente",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = EmployeeResponseDTO.class)
                )
            ),
            @ApiResponse(
                responseCode = "400",
                description = "Datos de entrada inválidos o relación jerárquica no válida",
                content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                responseCode = "401",
                description = "Token de autenticación no válido o ausente",
                content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                responseCode = "403",
                description = "Sin permisos suficientes - Se requiere rol ADMIN o GESTOR",
                content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                responseCode = "500",
                description = "Error interno del servidor",
                content = @Content(mediaType = "application/json")
            )
        })
        @PostMapping
        @PreAuthorize("hasAnyRole('ADMIN', 'GESTOR')")
        public ResponseEntity<EmployeeResponseDTO> save(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Datos del nuevo empleado a registrar",
                required = true,
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = EmployeeRequestDTO.class)
                )
            )
            @Valid @RequestBody EmployeeRequestDTO dto) {
        EmployeeResponseDTO created = employeeService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
        }

        @Operation(
            summary = "Actualizar empleado",
            description = "Modifica los datos de un empleado existente. Soporta tanto actualización completa (PUT) " +
                "como parcial (PATCH). Solo usuarios con permisos administrativos (ADMIN) o de gestión (GESTOR) " +
                "pueden actualizar empleados.",
            security = @SecurityRequirement(name = "bearer")
        )
        @ApiResponses({
            @ApiResponse(
                responseCode = "200",
                description = "Empleado actualizado exitosamente",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = EmployeeResponseDTO.class)
                )
            ),
            @ApiResponse(
                responseCode = "400",
                description = "Datos de entrada inválidos",
                content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                responseCode = "401",
                description = "Token de autenticación no válido o ausente",
                content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                responseCode = "403",
                description = "Sin permisos suficientes - Se requiere rol ADMIN o GESTOR",
                content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                responseCode = "404",
                description = "Empleado no encontrado",
                content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                responseCode = "500",
                description = "Error interno del servidor",
                content = @Content(mediaType = "application/json")
            )
        })
        @PatchMapping("/{id}")
        @PutMapping("/{id}")
        @PreAuthorize("hasAnyRole('ADMIN', 'GESTOR')")
        public ResponseEntity<EmployeeResponseDTO> update(
            @Parameter(
                description = "ID único del empleado a actualizar",
                required = true,
                example = "5"
            )
            @PathVariable Short id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Datos actualizados del empleado",
                required = true,
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = EmployeeRequestDTO.class)
                )
            )
            @RequestBody EmployeeRequestDTO dto) {
        return ResponseEntity.ok(employeeService.update(id, dto));
        }

        @Operation(
            summary = "Eliminar empleado",
            description = "Elimina permanentemente un empleado del sistema. Esta operación es irreversible y " +
                "solo puede ser realizada por administradores o usuarios con permisos de gestión. Se debe " +
                "verificar previamente que no existan dependencias funcionales asociadas al empleado.",
            security = @SecurityRequirement(name = "bearer")
        )
        @ApiResponses({
            @ApiResponse(
                responseCode = "204",
                description = "Empleado eliminado exitosamente"
            ),
            @ApiResponse(
                responseCode = "400",
                description = "Datos de entrada inválidos",
                content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                responseCode = "401",
                description = "Token de autenticación no válido o ausente",
                content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                responseCode = "403",
                description = "Sin permisos suficientes - Se requiere rol ADMIN o GESTOR",
                content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                responseCode = "404",
                description = "Empleado no encontrado",
                content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                responseCode = "409",
                description = "Conflicto - No se puede eliminar un empleado con referencias activas",
                content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                responseCode = "500",
                description = "Error interno del servidor",
                content = @Content(mediaType = "application/json")
            )
        })
        @DeleteMapping("/{id}")
        @PreAuthorize("hasAnyRole('ADMIN', 'GESTOR')")
        public ResponseEntity<Void> delete(
            @Parameter(
                description = "ID único del empleado a eliminar",
                required = true,
                example = "5"
            )
            @PathVariable Short id) {
        employeeService.delete(id);
        return ResponseEntity.noContent().build();
        }
}
