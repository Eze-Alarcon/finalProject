package org.ezequiel.proyectofinal.features.catalog.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.ezequiel.proyectofinal.features.catalog.dto.ProductLowStockResponseDTO;
import org.ezequiel.proyectofinal.features.catalog.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/inventory")
@RequiredArgsConstructor
@Tag(name = "Catalog - Inventory", description = "API de gestión de existencias del catálogo. Expone alertas de stock bajo a partir de la información disponible en el sistema sin asumir reglas adicionales de inventario.")
public class InventoryController {

    private final ProductService productService;

        @Operation(
            summary = "Obtener alertas de stock bajo",
            description = "Recupera la lista de productos detectados con stock bajo según la lógica implementada en el servicio. La respuesta incluye únicamente datos de producto y el nombre del proveedor cuando está disponible en el modelo de salida.",
            security = @SecurityRequirement(name = "bearer")
        )
        @ApiResponses({
            @ApiResponse(
                responseCode = "200",
                description = "Alertas de stock bajo recuperadas exitosamente",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(type = "array", implementation = ProductLowStockResponseDTO.class)
                )
            ),
            @ApiResponse(responseCode = "401", description = "Token de autenticación no válido o ausente", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "403", description = "Sin permisos suficientes - Se requiere rol ADMIN, WAREHOUSE, GESTOR o CONSULTA", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "No se encontraron alertas de stock bajo", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(mediaType = "application/json"))
        })
        @GetMapping("/alerts/low-stock")
    @PreAuthorize("hasAnyRole('ADMIN', 'WAREHOUSE', 'GESTOR', 'CONSULTA')")
    public ResponseEntity<List<ProductLowStockResponseDTO>> getLowStockAlerts() {
        return ResponseEntity.ok(productService.getLowStockAlerts());
    }
}
