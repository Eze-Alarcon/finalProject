package org.ezequiel.proyectofinal.features.catalog.controller;

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
public class InventoryController {

    private final ProductService productService;

    @GetMapping("/alerts/low-stock")
    @PreAuthorize("hasAnyRole('ADMIN', 'WAREHOUSE', 'GESTOR', 'CONSULTA')")
    public ResponseEntity<List<ProductLowStockResponseDTO>> getLowStockAlerts() {
        return ResponseEntity.ok(productService.getLowStockAlerts());
    }
}
