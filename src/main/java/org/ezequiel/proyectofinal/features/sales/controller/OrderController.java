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
import org.ezequiel.proyectofinal.features.sales.dto.OrderRequestDTO;
import org.ezequiel.proyectofinal.features.sales.dto.OrderResponseDTO;
import org.ezequiel.proyectofinal.features.sales.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
@Tag(name = "Sales - Orders", description = "API de gestión de órdenes de venta del sistema. Permite crear, consultar, actualizar órdenes, así como gestionar su ciclo de vida incluyendo envío y cancelación. Las órdenes conectan clientes, empleados, productos y transportistas.")
public class OrderController {

    private final OrderService orderService;

    @Operation(
            summary = "Obtener todas las órdenes",
            description = "Recupera la lista completa de órdenes registradas en el sistema de ventas. Incluye información " +
                    "completa de cada orden con sus detalles de productos, cliente, empleado asignado y estado. " +
                    "Accesible para roles administrativos y operativos.",
            security = @SecurityRequirement(name = "bearer")
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200", 
                    description = "Lista de órdenes recuperada exitosamente",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = OrderResponseDTO.class)
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
    public ResponseEntity<List<OrderResponseDTO>> findAll() {
        return ResponseEntity.ok(orderService.findAll());
    }

    @Operation(
            summary = "Obtener orden por ID",
            description = "Recupera la información detallada de una orden específica mediante su ID único. " +
                    "Incluye todos los detalles de productos, información del cliente, empleado asignado, " +
                    "datos de envío y estado actual de la orden.",
            security = @SecurityRequirement(name = "bearer")
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200", 
                    description = "Orden encontrada y datos recuperados exitosamente",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = OrderResponseDTO.class)
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
                    description = "Orden no encontrada",
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
    public ResponseEntity<OrderResponseDTO> findById(
            @Parameter(
                    description = "ID único de la orden a consultar", 
                    required = true,
                    example = "10248"
            )
            @PathVariable Short id) {
        return ResponseEntity.ok(orderService.findById(id));
    }

    @Operation(
            summary = "Crear nueva orden",
            description = "Registra una nueva orden de venta en el sistema. La orden debe incluir información del " +
                    "cliente, empleado asignado, detalles de productos y datos de envío. El sistema asignará " +
                    "automáticamente un ID único y establecerá el estado inicial. Solo usuarios con permisos " +
                    "de gestión pueden crear órdenes.",
            security = @SecurityRequirement(name = "bearer")
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201", 
                    description = "Orden creada exitosamente",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = OrderResponseDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400", 
                    description = "Datos de entrada inválidos o referencias no válidas (cliente, empleado, productos)",
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
    public ResponseEntity<OrderResponseDTO> save(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos de la nueva orden incluyendo detalles de productos y información de envío",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = OrderRequestDTO.class)
                    )
            )
            @Valid @RequestBody OrderRequestDTO dto) {
        OrderResponseDTO created = orderService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @Operation(
            summary = "Actualizar orden",
            description = "Modifica los datos de una orden existente. Permite actualizar información del cliente, " +
                    "detalles de productos, datos de envío y otros campos modificables. Solo órdenes en " +
                    "estados que permitan modificación pueden ser actualizadas.",
            security = @SecurityRequirement(name = "bearer")
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200", 
                    description = "Orden actualizada exitosamente",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = OrderResponseDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400", 
                    description = "Datos de entrada inválidos o estado de orden no permite modificación",
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
                    description = "Orden no encontrada",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "500", 
                    description = "Error interno del servidor",
                    content = @Content(mediaType = "application/json")
            )
    })
    @PatchMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'WAREHOUSE', 'GESTOR')")
    public ResponseEntity<OrderResponseDTO> update(
            @Parameter(
                    description = "ID único de la orden a actualizar", 
                    required = true,
                    example = "10248"
            )
            @PathVariable Short id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos actualizados de la orden",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = OrderRequestDTO.class)
                    )
            )
            @RequestBody OrderRequestDTO dto) {
        return ResponseEntity.ok(orderService.update(id, dto));
    }

    @Operation(
            summary = "Marcar orden como enviada",
            description = "Cambia el estado de una orden a 'enviada' y registra la fecha de envío. " +
                    "Esta acción solo puede ser realizada por usuarios con permisos de almacén o administrativos, " +
                    "ya que requiere confirmación de que los productos han sido despachados físicamente.",
            security = @SecurityRequirement(name = "bearer")
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200", 
                    description = "Orden marcada como enviada exitosamente",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = OrderResponseDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400", 
                    description = "La orden no puede ser enviada en su estado actual",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "401", 
                    description = "Token de autenticación no válido o ausente",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "403", 
                    description = "Sin permisos suficientes - Se requiere rol ADMIN o WAREHOUSE",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "404", 
                    description = "Orden no encontrada",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "500", 
                    description = "Error interno del servidor",
                    content = @Content(mediaType = "application/json")
            )
    })
    @PatchMapping("/{id}/ship")
    @PreAuthorize("hasAnyRole('ADMIN', 'WAREHOUSE')")
    public ResponseEntity<OrderResponseDTO> shipOrder(
            @Parameter(
                    description = "ID único de la orden a marcar como enviada", 
                    required = true,
                    example = "10248"
            )
            @PathVariable Short id) {
        return ResponseEntity.ok(orderService.shipOrder(id));
    }

    @Operation(
            summary = "Cancelar orden",
            description = "Cambia el estado de una orden a 'cancelada'. Esta acción puede realizarse cuando " +
                    "la orden aún no ha sido procesada o enviada. Una vez cancelada, la orden no puede " +
                    "ser procesada para envío. Accesible para roles de gestión y administrativos.",
            security = @SecurityRequirement(name = "bearer")
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200", 
                    description = "Orden cancelada exitosamente",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = OrderResponseDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400", 
                    description = "La orden no puede ser cancelada en su estado actual (ej: ya enviada)",
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
                    description = "Orden no encontrada",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "500", 
                    description = "Error interno del servidor",
                    content = @Content(mediaType = "application/json")
            )
    })
    @PatchMapping("/{id}/cancel")
    @PreAuthorize("hasAnyRole('ADMIN', 'WAREHOUSE', 'GESTOR')")
    public ResponseEntity<OrderResponseDTO> cancelOrder(
            @Parameter(
                    description = "ID único de la orden a cancelar", 
                    required = true,
                    example = "10248"
            )
            @PathVariable Short id) {
        return ResponseEntity.ok(orderService.cancelOrder(id));
    }

    @Operation(
            summary = "Eliminar orden (DEPRECADO)",
            description = "Elimina permanentemente una orden del sistema. ⚠️ ESTE ENDPOINT ESTÁ DEPRECADO y se " +
                    "desaconseja su uso. En su lugar, utilice el endpoint de cancelación de órdenes (/cancel) " +
                    "para mantener la integridad del historial de ventas. Solo administradores y personal de " +
                    "almacén pueden realizar esta operación irreversible.",
            deprecated = true,
            security = @SecurityRequirement(name = "bearer")
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204", 
                    description = "Orden eliminada exitosamente"
            ),
            @ApiResponse(
                    responseCode = "401", 
                    description = "Token de autenticación no válido o ausente",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "403", 
                    description = "Sin permisos suficientes - Se requiere rol ADMIN o WAREHOUSE",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "404", 
                    description = "Orden no encontrada",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "409", 
                    description = "Conflicto - No se puede eliminar orden con referencias activas",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "500", 
                    description = "Error interno del servidor",
                    content = @Content(mediaType = "application/json")
            )
    })
    @Deprecated
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'WAREHOUSE')")
    public ResponseEntity<Void> delete(
            @Parameter(
                    description = "ID único de la orden a eliminar", 
                    required = true,
                    example = "10248"
            )
            @PathVariable Short id) {
        orderService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
