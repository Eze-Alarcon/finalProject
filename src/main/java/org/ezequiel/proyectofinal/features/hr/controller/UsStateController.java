package org.ezequiel.proyectofinal.features.hr.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.ezequiel.proyectofinal.features.hr.dto.UsStateRequestDTO;
import org.ezequiel.proyectofinal.features.hr.dto.UsStateResponseDTO;
import org.ezequiel.proyectofinal.features.hr.service.UsStateService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/us-states")
@RequiredArgsConstructor
public class UsStateController {

    private final UsStateService usStateService;

    @GetMapping
    public ResponseEntity<List<UsStateResponseDTO>> findAll() {
        return ResponseEntity.ok(usStateService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsStateResponseDTO> findById(@PathVariable Short id) {
        return ResponseEntity.ok(usStateService.findById(id));
    }

    @PostMapping
    public ResponseEntity<UsStateResponseDTO> save(@Valid @RequestBody UsStateRequestDTO dto) {
        UsStateResponseDTO created = usStateService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsStateResponseDTO> update(
            @PathVariable Short id,
            @Valid @RequestBody UsStateRequestDTO dto) {
        return ResponseEntity.ok(usStateService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Short id) {
        usStateService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
