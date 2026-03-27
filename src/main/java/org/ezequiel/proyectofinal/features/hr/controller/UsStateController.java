package org.ezequiel.proyectofinal.features.hr.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.ezequiel.proyectofinal.features.hr.dto.UsStateRequestDTO;
import org.ezequiel.proyectofinal.features.hr.dto.UsStateResponseDTO;
import org.ezequiel.proyectofinal.features.hr.service.UsStateService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/us-states")
@RequiredArgsConstructor
public class UsStateController {

    private final UsStateService usStateService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'GESTOR', 'CONSULTA')")
    public ResponseEntity<List<UsStateResponseDTO>> findAll() {
        return ResponseEntity.ok(usStateService.findAll());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'GESTOR', 'CONSULTA')")
    public ResponseEntity<UsStateResponseDTO> findById(@PathVariable Short id) {
        return ResponseEntity.ok(usStateService.findById(id));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'GESTOR')")
    public ResponseEntity<UsStateResponseDTO> save(@Valid @RequestBody UsStateRequestDTO dto) {
        UsStateResponseDTO created = usStateService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'GESTOR')")
    public ResponseEntity<UsStateResponseDTO> update(
            @PathVariable Short id,
            @Valid @RequestBody UsStateRequestDTO dto) {
        return ResponseEntity.ok(usStateService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'GESTOR')")
    public ResponseEntity<Void> delete(@PathVariable Short id) {
        usStateService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
