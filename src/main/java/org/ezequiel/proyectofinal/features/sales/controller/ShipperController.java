package org.ezequiel.proyectofinal.features.sales.controller;

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
public class ShipperController {

    private final ShipperService shipperService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'WAREHOUSE', 'GESTOR', 'CONSULTA')")
    public ResponseEntity<List<ShipperResponseDTO>> findAll() {
        return ResponseEntity.ok(shipperService.findAll());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'WAREHOUSE', 'GESTOR', 'CONSULTA')")
    public ResponseEntity<ShipperResponseDTO> findById(@PathVariable Short id) {
        return ResponseEntity.ok(shipperService.findById(id));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'WAREHOUSE', 'GESTOR')")
    public ResponseEntity<ShipperResponseDTO> save(@Valid @RequestBody ShipperRequestDTO dto) {
        ShipperResponseDTO created = shipperService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PatchMapping("/{id}")
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'WAREHOUSE', 'GESTOR')")
    public ResponseEntity<ShipperResponseDTO> update(
            @PathVariable Short id,
            @RequestBody ShipperRequestDTO dto) {
        return ResponseEntity.ok(shipperService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Short id) {
        shipperService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
