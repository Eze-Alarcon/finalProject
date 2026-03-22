package org.ezequiel.proyectofinal.features.hr.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.ezequiel.proyectofinal.features.hr.dto.RegionRequestDTO;
import org.ezequiel.proyectofinal.features.hr.dto.RegionResponseDTO;
import org.ezequiel.proyectofinal.features.hr.service.RegionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/regions")
@RequiredArgsConstructor
public class RegionController {

    private final RegionService regionService;

    @GetMapping
    public ResponseEntity<List<RegionResponseDTO>> findAll() {
        return ResponseEntity.ok(regionService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RegionResponseDTO> findById(@PathVariable Short id) {
        return ResponseEntity.ok(regionService.findById(id));
    }

    @PostMapping
    public ResponseEntity<RegionResponseDTO> save(@Valid @RequestBody RegionRequestDTO dto) {
        RegionResponseDTO created = regionService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RegionResponseDTO> update(
            @PathVariable Short id,
            @Valid @RequestBody RegionRequestDTO dto) {
        return ResponseEntity.ok(regionService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Short id) {
        regionService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
