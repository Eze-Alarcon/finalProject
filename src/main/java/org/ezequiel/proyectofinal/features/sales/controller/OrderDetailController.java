package org.ezequiel.proyectofinal.features.sales.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.ezequiel.proyectofinal.features.sales.dto.OrderDetailResponseDTO;
import org.ezequiel.proyectofinal.features.sales.service.OrderDetailService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/order-details")
@RequiredArgsConstructor
@Tag(name = "Sales - Order Details", description = "API de gestión de detalles de órdenes del sistema de ventas. Permite consultar y eliminar líneas de producto asociadas a una orden específica. Cada detalle pertenece a la relación entre Order y Product.")
public class OrderDetailController {

    private final OrderDetailService orderDetailService;

        @Operation(
            summary = "Obtener todos los detalles de órdenes",
            description = "Recupera la lista completa de detalles de órdenes registrados en el sistema. " +
                "Cada elemento representa una línea de producto dentro de una orden y muestra la relación " +
                "entre la orden y el producto asociado. Accesible para roles administrativos y operativos.",
            security = @SecurityRequirement(name = "bearer")
        )
        @ApiResponses({
            @ApiResponse(
                responseCode = "200",
                description = "Lista de detalles de órdenes recuperada exitosamente",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = OrderDetailResponseDTO.class)
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
        public ResponseEntity<List<OrderDetailResponseDTO>> findAll() {
        return ResponseEntity.ok(orderDetailService.findAll());
        }

        @Operation(
            summary = "Obtener detalle de orden por IDs",
            description = "Recupera la información detallada de una línea de producto específica dentro de una orden. " +
                "El recurso se identifica por la combinación de ID de orden e ID de producto, reflejando la relación " +
                "entre Order y Product. Accesible para roles con permisos de consulta y gestión.",
            security = @SecurityRequirement(name = "bearer")
        )
        @ApiResponses({
            @ApiResponse(
                responseCode = "200",
                description = "Detalle de orden encontrado y datos recuperados exitosamente",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = OrderDetailResponseDTO.class)
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
                description = "Sin permisos suficientes - Se requiere rol ADMIN, WAREHOUSE, GESTOR o CONSULTA",
                content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                responseCode = "404",
                description = "Detalle de orden no encontrado",
                content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                responseCode = "500",
                description = "Error interno del servidor",
                content = @Content(mediaType = "application/json")
            )
        })
        @GetMapping("/order/{orderId}/product/{productId}")
        @PreAuthorize("hasAnyRole('ADMIN', 'WAREHOUSE', 'GESTOR', 'CONSULTA')")
        public ResponseEntity<OrderDetailResponseDTO> findById(
            @Parameter(
                description = "ID único de la orden a la que pertenece el detalle",
                required = true,
                example = "10248"
            )
            @PathVariable Short orderId,
            @Parameter(
                description = "ID único del producto dentro de la orden",
                required = true,
                example = "11"
            )
            @PathVariable Short productId) {
        return ResponseEntity.ok(orderDetailService.findById(orderId, productId));
        }

        @Operation(
            summary = "Eliminar detalle de orden (DEPRECADO)",
            description = "Elimina permanentemente un detalle de orden identificado por la combinación de orden y producto. " +
                "ESTE ENDPOINT ESTÁ DEPRECADO y se desaconseja su uso. Se recomienda utilizar el flujo de " +
                "actualización de la orden para mantener la integridad de sus líneas de producto. Solo administradores " +
                "y personal de almacén pueden realizar esta operación irreversible.",
            deprecated = true,
            security = @SecurityRequirement(name = "bearer")
        )
        @ApiResponses({
            @ApiResponse(
                responseCode = "204",
                description = "Detalle de orden eliminado exitosamente"
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
                description = "Sin permisos suficientes - Se requiere rol ADMIN o WAREHOUSE",
                content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                responseCode = "404",
                description = "Detalle de orden no encontrado",
                content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                responseCode = "409",
                description = "Conflicto - No se puede eliminar un detalle en un estado que lo impida",
                content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                responseCode = "500",
                description = "Error interno del servidor",
                content = @Content(mediaType = "application/json")
            )
        })
        @Deprecated
        @DeleteMapping("/order/{orderId}/product/{productId}")
        @PreAuthorize("hasAnyRole('ADMIN', 'WAREHOUSE')")
        public ResponseEntity<Void> delete(
            @Parameter(
                description = "ID único de la orden a la que pertenece el detalle",
                required = true,
                example = "10248"
            )
            @PathVariable Short orderId,
            @Parameter(
                description = "ID único del producto dentro de la orden",
                required = true,
                example = "11"
            )
            @PathVariable Short productId) {
        orderDetailService.delete(orderId, productId);
        return ResponseEntity.noContent().build();
        }
}
