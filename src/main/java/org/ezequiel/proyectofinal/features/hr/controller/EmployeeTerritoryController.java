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
import org.ezequiel.proyectofinal.features.hr.dto.EmployeeTerritoryRequestDTO;
import org.ezequiel.proyectofinal.features.hr.dto.EmployeeTerritoryResponseDTO;
import org.ezequiel.proyectofinal.features.hr.service.EmployeeTerritoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/employee-territories")
@RequiredArgsConstructor
@Tag(name = "HR - Employee Territories", description = "API de gestión de la relación entre empleados y territorios. Permite consultar, asignar y eliminar la relación entre un empleado y un territorio específico sin asumir jerarquías ni estructuras adicionales.")
public class EmployeeTerritoryController {

    private final EmployeeTerritoryService employeeTerritoryService;

        @Operation(
            summary = "Obtener todas las relaciones empleado-territorio",
            description = "Recupera la lista completa de relaciones registradas entre empleados y territorios. " +
                "Cada elemento representa una asignación entre un empleado y un territorio concreto. " +
                "Accesible para roles administrativos y de consulta.",
            security = @SecurityRequirement(name = "bearer")
        )
        @ApiResponses({
            @ApiResponse(
                responseCode = "200",
                description = "Lista de relaciones empleado-territorio recuperada exitosamente",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = EmployeeTerritoryResponseDTO.class)
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
        public ResponseEntity<List<EmployeeTerritoryResponseDTO>> findAll() {
        return ResponseEntity.ok(employeeTerritoryService.findAll());
        }

        @Operation(
            summary = "Obtener relación de territorios por empleado",
            description = "Recupera la relación de territorios asociada a un empleado específico mediante su ID. " +
                "Este recurso expresa la asignación entre Employee y Territory sin asumir estructuras adicionales. " +
                "Accesible para roles con permisos de consulta.",
            security = @SecurityRequirement(name = "bearer")
        )
        @ApiResponses({
            @ApiResponse(
                responseCode = "200",
                description = "Relación empleado-territorio recuperada exitosamente",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = EmployeeTerritoryResponseDTO.class)
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
                description = "Relación empleado-territorio no encontrada",
                content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                responseCode = "500",
                description = "Error interno del servidor",
                content = @Content(mediaType = "application/json")
            )
        })
        @GetMapping("/employee/{employeeId}")
        @PreAuthorize("hasAnyRole('ADMIN', 'GESTOR', 'CONSULTA')")
        public ResponseEntity<EmployeeTerritoryResponseDTO> findByEmployeeId(
            @Parameter(
                description = "ID único del empleado cuya relación territorial se desea consultar",
                required = true,
                example = "5"
            )
            @PathVariable Short employeeId) {
        return ResponseEntity.ok(employeeTerritoryService.findByEmployeeId(employeeId));
        }

        @Operation(
            summary = "Asignar territorio a empleado",
            description = "Registra una nueva relación entre un empleado y un territorio. Solo usuarios con permisos " +
                "administrativos (ADMIN) o de gestión (GESTOR) pueden crear esta asignación. El recurso representa " +
                "la vinculación directa entre ambos identificadores.",
            security = @SecurityRequirement(name = "bearer")
        )
        @ApiResponses({
            @ApiResponse(
                responseCode = "201",
                description = "Relación empleado-territorio creada exitosamente",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = EmployeeTerritoryResponseDTO.class)
                )
            ),
            @ApiResponse(
                responseCode = "400",
                description = "Datos de entrada inválidos o relación ya existente",
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
        public ResponseEntity<EmployeeTerritoryResponseDTO> save(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Datos de la nueva relación entre empleado y territorio",
                required = true,
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = EmployeeTerritoryRequestDTO.class)
                )
            )
            @Valid @RequestBody EmployeeTerritoryRequestDTO dto) {
        EmployeeTerritoryResponseDTO created = employeeTerritoryService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
        }

        @Operation(
            summary = "Eliminar relación empleado-territorio",
            description = "Elimina permanentemente la relación entre un empleado y un territorio identificados por " +
                "su combinación de IDs. Esta operación es irreversible y solo puede ser realizada por usuarios " +
                "con permisos administrativos o de gestión.",
            security = @SecurityRequirement(name = "bearer")
        )
        @ApiResponses({
            @ApiResponse(
                responseCode = "204",
                description = "Relación empleado-territorio eliminada exitosamente"
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
                description = "Relación empleado-territorio no encontrada",
                content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                responseCode = "500",
                description = "Error interno del servidor",
                content = @Content(mediaType = "application/json")
            )
        })
        @DeleteMapping("/employee/{employeeId}/territory/{territoryId}")
        @PreAuthorize("hasAnyRole('ADMIN', 'GESTOR')")
        public ResponseEntity<Void> delete(
            @Parameter(
                description = "ID único del empleado asociado a la relación",
                required = true,
                example = "5"
            )
            @PathVariable Short employeeId,
            @Parameter(
                description = "ID del territorio asociado a la relación",
                required = true,
                example = "01581"
            )
            @PathVariable String territoryId) {
        employeeTerritoryService.delete(employeeId, territoryId);
        return ResponseEntity.noContent().build();
        }
}
