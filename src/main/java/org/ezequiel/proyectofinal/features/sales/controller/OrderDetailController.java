package org.ezequiel.proyectofinal.features.sales.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.ezequiel.proyectofinal.features.sales.dto.OrderDetailRequestDTO;
import org.ezequiel.proyectofinal.features.sales.dto.OrderDetailResponseDTO;
import org.ezequiel.proyectofinal.features.sales.service.OrderDetailService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/order-details")
@RequiredArgsConstructor
public class OrderDetailController {

    private final OrderDetailService orderDetailService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'WAREHOUSE', 'GESTOR', 'CONSULTA')")
    public ResponseEntity<List<OrderDetailResponseDTO>> findAll() {
        return ResponseEntity.ok(orderDetailService.findAll());
    }

    @GetMapping("/order/{orderId}/product/{productId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'WAREHOUSE', 'GESTOR', 'CONSULTA')")
    public ResponseEntity<OrderDetailResponseDTO> findById(
            @PathVariable Short orderId,
            @PathVariable Short productId) {
        return ResponseEntity.ok(orderDetailService.findById(orderId, productId));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'WAREHOUSE')")
    public ResponseEntity<OrderDetailResponseDTO> save(
            @Valid @RequestBody OrderDetailRequestDTO dto) {
        OrderDetailResponseDTO created = orderDetailService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/order/{orderId}/product/{productId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'WAREHOUSE')")
    public ResponseEntity<OrderDetailResponseDTO> update(
            @PathVariable Short orderId,
            @PathVariable Short productId,
            @Valid @RequestBody OrderDetailRequestDTO dto) {
        return ResponseEntity.ok(orderDetailService.update(orderId, productId, dto));
    }

    @DeleteMapping("/order/{orderId}/product/{productId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'WAREHOUSE')")
    public ResponseEntity<Void> delete(
            @PathVariable Short orderId,
            @PathVariable Short productId) {
        orderDetailService.delete(orderId, productId);
        return ResponseEntity.noContent().build();
    }
}
