package org.ezequiel.proyectofinal.features.hr.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.ezequiel.proyectofinal.features.hr.dto.EmployeeTerritoryRequestDTO;
import org.ezequiel.proyectofinal.features.hr.dto.EmployeeTerritoryResponseDTO;
import org.ezequiel.proyectofinal.features.hr.service.EmployeeTerritoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/employee-territories")
@RequiredArgsConstructor
public class EmployeeTerritoryController {

    private final EmployeeTerritoryService employeeTerritoryService;

    @GetMapping
    public ResponseEntity<List<EmployeeTerritoryResponseDTO>> findAll() {
        return ResponseEntity.ok(employeeTerritoryService.findAll());
    }

    @GetMapping("/employee/{employeeId}/territory/{territoryId}")
    public ResponseEntity<EmployeeTerritoryResponseDTO> findById(
            @PathVariable Short employeeId,
            @PathVariable String territoryId) {
        return ResponseEntity.ok(employeeTerritoryService.findById(employeeId, territoryId));
    }

    @PostMapping
    public ResponseEntity<EmployeeTerritoryResponseDTO> save(
            @Valid @RequestBody EmployeeTerritoryRequestDTO dto) {
        EmployeeTerritoryResponseDTO created = employeeTerritoryService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @DeleteMapping("/employee/{employeeId}/territory/{territoryId}")
    public ResponseEntity<Void> delete(
            @PathVariable Short employeeId,
            @PathVariable String territoryId) {
        employeeTerritoryService.delete(employeeId, territoryId);
        return ResponseEntity.noContent().build();
    }
}
