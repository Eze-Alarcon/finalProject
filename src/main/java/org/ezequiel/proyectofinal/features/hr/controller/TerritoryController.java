package org.ezequiel.proyectofinal.features.hr.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.ezequiel.proyectofinal.features.hr.dto.TerritoryRequestDTO;
import org.ezequiel.proyectofinal.features.hr.dto.TerritoryResponseDTO;
import org.ezequiel.proyectofinal.features.hr.service.TerritoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/territories")
@RequiredArgsConstructor
public class TerritoryController {

    private final TerritoryService territoryService;

    @GetMapping
    public ResponseEntity<List<TerritoryResponseDTO>> findAll() {
        return ResponseEntity.ok(territoryService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TerritoryResponseDTO> findById(@PathVariable String id) {
        return ResponseEntity.ok(territoryService.findById(id));
    }

    @PostMapping
    public ResponseEntity<TerritoryResponseDTO> save(@Valid @RequestBody TerritoryRequestDTO dto) {
        TerritoryResponseDTO created = territoryService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TerritoryResponseDTO> update(
            @PathVariable String id,
            @Valid @RequestBody TerritoryRequestDTO dto) {
        return ResponseEntity.ok(territoryService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        territoryService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
