package org.ezequiel.proyectofinal.features.catalog.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.ezequiel.proyectofinal.features.catalog.dto.SupplierRequestDTO;
import org.ezequiel.proyectofinal.features.catalog.dto.SupplierResponseDTO;
import org.ezequiel.proyectofinal.features.catalog.service.SupplierService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/suppliers")
@RequiredArgsConstructor
public class SupplierController {

    private final SupplierService supplierService;

    @GetMapping
    public ResponseEntity<List<SupplierResponseDTO>> findAll() {
        return ResponseEntity.ok(supplierService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SupplierResponseDTO> findById(@PathVariable Short id) {
        return ResponseEntity.ok(supplierService.findById(id));
    }

    @PostMapping
    public ResponseEntity<SupplierResponseDTO> save(@Valid @RequestBody SupplierRequestDTO dto) {
        SupplierResponseDTO created = supplierService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SupplierResponseDTO> update(
            @PathVariable Short id,
            @Valid @RequestBody SupplierRequestDTO dto) {
        return ResponseEntity.ok(supplierService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Short id) {
        supplierService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
