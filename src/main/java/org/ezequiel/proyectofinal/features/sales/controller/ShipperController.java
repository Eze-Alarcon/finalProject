package org.ezequiel.proyectofinal.features.sales.controller;

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
import org.ezequiel.proyectofinal.features.sales.dto.ShipperRequestDTO;
import org.ezequiel.proyectofinal.features.sales.dto.ShipperResponseDTO;
import org.ezequiel.proyectofinal.features.sales.service.ShipperService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/shippers")
@RequiredArgsConstructor
@Tag(name = "Sales - Shipper", description = "API de gestión de empresas transportistas del sistema de ventas. Permite crear, consultar, actualizar y eliminar transportistas que manejan los envíos de órdenes.")
public class ShipperController {

    private final ShipperService shipperService;

    @Operation(
            summary = "Obtener todas las empresas transportistas",
            description = "Recupera la lista completa de empresas transportistas registradas en el sistema. Accesible para roles " +
                    "administrativos y operativos que requieren consultar información de transportistas para gestionar envíos.",
            security = @SecurityRequirement(name = "bearer")
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200", 
                    description = "Lista de transportistas recuperada exitosamente",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ShipperResponseDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "401", 
                    description = "Token de autenticación no válido o ausente",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "403", 
                    description = "Sin permisos suficientes - Se requiere rol ADMIN, WAREHOUSE, GESTOR o CONSULTA",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "500", 
                    description = "Error interno del servidor",
                    content = @Content(mediaType = "application/json")
            )
    })
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'WAREHOUSE', 'GESTOR', 'CONSULTA')")
    public ResponseEntity<List<ShipperResponseDTO>> findAll() {
        return ResponseEntity.ok(shipperService.findAll());
    }

    @Operation(
            summary = "Obtener transportista por ID",
            description = "Recupera la información detallada de una empresa transportista específica mediante su ID único. " +
                    "Accesible para roles con permisos de consulta y gestión de envíos.",
            security = @SecurityRequirement(name = "bearer")
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200", 
                    description = "Transportista encontrado y datos recuperados exitosamente",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ShipperResponseDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "401", 
                    description = "Token de autenticación no válido o ausente",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "403", 
                    description = "Sin permisos suficientes - Se requiere rol ADMIN, WAREHOUSE, GESTOR o CONSULTA",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "404", 
                    description = "Transportista no encontrado",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "500", 
                    description = "Error interno del servidor",
                    content = @Content(mediaType = "application/json")
            )
    })
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'WAREHOUSE', 'GESTOR', 'CONSULTA')")
    public ResponseEntity<ShipperResponseDTO> findById(
            @Parameter(
                    description = "ID único del transportista a consultar", 
                    required = true,
                    example = "1"
            )
            @PathVariable Short id) {
        return ResponseEntity.ok(shipperService.findById(id));
    }

    @Operation(
            summary = "Crear nueva empresa transportista",
            description = "Registra una nueva empresa transportista en el sistema de ventas. Solo usuarios con permisos " +
                    "administrativos (ADMIN), de almacén (WAREHOUSE) o de gestión (GESTOR) pueden crear transportistas. " +
                    "Los transportistas son esenciales para la gestión de envíos de órdenes.",
            security = @SecurityRequirement(name = "bearer")
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201", 
                    description = "Transportista creado exitosamente",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ShipperResponseDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400", 
                    description = "Datos de entrada inválidos o transportista ya existe",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "401", 
                    description = "Token de autenticación no válido o ausente",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "403", 
                    description = "Sin permisos suficientes - Se requiere rol ADMIN, WAREHOUSE o GESTOR",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "500", 
                    description = "Error interno del servidor",
                    content = @Content(mediaType = "application/json")
            )
    })
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'WAREHOUSE', 'GESTOR')")
    public ResponseEntity<ShipperResponseDTO> save(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos de la nueva empresa transportista a registrar",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ShipperRequestDTO.class)
                    )
            )
            @Valid @RequestBody ShipperRequestDTO dto) {
        ShipperResponseDTO created = shipperService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @Operation(
            summary = "Actualizar transportista",
            description = "Modifica los datos de una empresa transportista existente. Soporta tanto actualización completa (PUT) " +
                    "como parcial (PATCH). Solo usuarios con permisos administrativos (ADMIN), de almacén (WAREHOUSE) " +
                    "o de gestión (GESTOR) pueden actualizar transportistas.",
            security = @SecurityRequirement(name = "bearer")
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200", 
                    description = "Transportista actualizado exitosamente",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ShipperResponseDTO.class)
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
                    description = "Sin permisos suficientes - Se requiere rol ADMIN, WAREHOUSE o GESTOR",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "404", 
                    description = "Transportista no encontrado",
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
    @PreAuthorize("hasAnyRole('ADMIN', 'WAREHOUSE', 'GESTOR')")
    public ResponseEntity<ShipperResponseDTO> update(
            @Parameter(
                    description = "ID único del transportista a actualizar", 
                    required = true,
                    example = "1"
            )
            @PathVariable Short id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos actualizados del transportista",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ShipperRequestDTO.class)
                    )
            )
            @RequestBody ShipperRequestDTO dto) {
        return ResponseEntity.ok(shipperService.update(id, dto));
    }

    @Operation(
            summary = "Eliminar transportista",
            description = "Elimina permanentemente una empresa transportista del sistema. Esta operación es irreversible y " +
                    "solo puede ser realizada por administradores. Se debe verificar que el transportista no tenga " +
                    "órdenes de envío activas antes de eliminar.",
            security = @SecurityRequirement(name = "bearer")
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204", 
                    description = "Transportista eliminado exitosamente"
            ),
            @ApiResponse(
                    responseCode = "401", 
                    description = "Token de autenticación no válido o ausente",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "403", 
                    description = "Sin permisos suficientes - Se requiere rol ADMIN",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "404", 
                    description = "Transportista no encontrado",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "409", 
                    description = "Conflicto - No se puede eliminar transportista con envíos activos",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "500", 
                    description = "Error interno del servidor",
                    content = @Content(mediaType = "application/json")
            )
    })
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<Void> delete(
            @Parameter(
                    description = "ID único del transportista a eliminar", 
                    required = true,
                    example = "1"
            )
            @PathVariable Short id) {
        shipperService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
