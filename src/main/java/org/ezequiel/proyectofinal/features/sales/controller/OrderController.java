package org.ezequiel.proyectofinal.features.sales.controller;

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
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'WAREHOUSE', 'GESTOR', 'CONSULTA')")
    public ResponseEntity<List<OrderResponseDTO>> findAll() {
        return ResponseEntity.ok(orderService.findAll());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'WAREHOUSE', 'GESTOR', 'CONSULTA')")
    public ResponseEntity<OrderResponseDTO> findById(@PathVariable Short id) {
        return ResponseEntity.ok(orderService.findById(id));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'WAREHOUSE', 'GESTOR')")
    public ResponseEntity<OrderResponseDTO> save(@Valid @RequestBody OrderRequestDTO dto) {
        OrderResponseDTO created = orderService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'WAREHOUSE', 'GESTOR')")
    public ResponseEntity<OrderResponseDTO> update(@PathVariable Short id, @RequestBody OrderRequestDTO dto) {
        return ResponseEntity.ok(orderService.update(id, dto));
    }

    @PatchMapping("/{id}/ship")
    @PreAuthorize("hasAnyRole('ADMIN', 'WAREHOUSE')")
    public ResponseEntity<OrderResponseDTO> shipOrder(@PathVariable Short id) {
        return ResponseEntity.ok(orderService.shipOrder(id));
    }

    @PatchMapping("/{id}/cancel")
    @PreAuthorize("hasAnyRole('ADMIN', 'WAREHOUSE', 'GESTOR')")
    public ResponseEntity<OrderResponseDTO> cancelOrder(@PathVariable Short id) {
        return ResponseEntity.ok(orderService.cancelOrder(id));
    }

    @Deprecated
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'WAREHOUSE')")
    public ResponseEntity<Void> delete(@PathVariable Short id) {
        orderService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
