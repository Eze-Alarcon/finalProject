package org.ezequiel.proyectofinal.features.hr.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.ezequiel.proyectofinal.features.hr.dto.TerritoryRequestDTO;
import org.ezequiel.proyectofinal.features.hr.dto.TerritoryResponseDTO;
import org.ezequiel.proyectofinal.features.hr.dto.TerritoryUpdateRequestDTO;
import org.ezequiel.proyectofinal.features.hr.service.TerritoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/territories")
@RequiredArgsConstructor
@Tag(name = "HR - Territories", description = "Sales territory management. Territories are geographic or business areas assigned to employees for sales and operations management. Each territory belongs to a specific Region.")
public class TerritoryController {

    private final TerritoryService territoryService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'GESTOR', 'CONSULTA')")
    @Operation(summary = "List all territories", description = "Retrieves a complete list of all sales territories with their associated region information. Available to ADMIN, GESTOR, and CONSULTA roles.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of territories retrieved successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(type = "array", implementation = TerritoryResponseDTO.class))),
            @ApiResponse(responseCode = "401", description = "Authentication required - Missing or invalid Bearer token"),
            @ApiResponse(responseCode = "403", description = "Insufficient permissions - User role does not allow access"),
            @ApiResponse(responseCode = "500", description = "Server error while processing the request")
    })
    @SecurityRequirement(name = "bearer-jwt")
    public ResponseEntity<List<TerritoryResponseDTO>> findAll() {
        return ResponseEntity.ok(territoryService.findAll());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'GESTOR', 'CONSULTA')")
    @Operation(summary = "Get territory by ID", description = "Retrieves detailed information about a specific sales territory including its associated region. Available to ADMIN, GESTOR, and CONSULTA roles.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Territory retrieved successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = TerritoryResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid territory ID format"),
            @ApiResponse(responseCode = "401", description = "Authentication required - Missing or invalid Bearer token"),
            @ApiResponse(responseCode = "403", description = "Insufficient permissions - User role does not allow access"),
            @ApiResponse(responseCode = "404", description = "Territory not found with provided ID"),
            @ApiResponse(responseCode = "500", description = "Server error while processing the request")
    })
    @SecurityRequirement(name = "bearer-jwt")
    public ResponseEntity<TerritoryResponseDTO> findById(
            @Parameter(name = "id", description = "Unique territory identifier (alphanumeric code)", example = "01001", required = true)
            @PathVariable String id) {
        return ResponseEntity.ok(territoryService.findById(id));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'GESTOR')")
    @Operation(summary = "Create a new territory", description = "Creates a new sales territory within a geographic region. Only ADMIN and GESTOR roles are allowed to create territories. Territory ID must be unique and the specified region must exist.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Territory created successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = TerritoryResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input - Territory ID, description required or territory ID exceeds maximum length"),
            @ApiResponse(responseCode = "401", description = "Authentication required - Missing or invalid Bearer token"),
            @ApiResponse(responseCode = "403", description = "Insufficient permissions - Only ADMIN and GESTOR can create territories"),
            @ApiResponse(responseCode = "409", description = "Conflict - Territory ID already exists or specified region not found"),
            @ApiResponse(responseCode = "500", description = "Server error while processing the request")
    })
    @SecurityRequirement(name = "bearer-jwt")
    public ResponseEntity<TerritoryResponseDTO> save(@Valid @RequestBody TerritoryRequestDTO dto) {
        TerritoryResponseDTO created = territoryService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'GESTOR')")
    @Operation(summary = "Update an existing territory", description = "Updates the territory description and/or assigned region. Only ADMIN and GESTOR roles can modify territories. Territory ID cannot be changed. Requires complete territory data.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Territory updated successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = TerritoryResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input - Territory description required or exceeds maximum length"),
            @ApiResponse(responseCode = "401", description = "Authentication required - Missing or invalid Bearer token"),
            @ApiResponse(responseCode = "403", description = "Insufficient permissions - Only ADMIN and GESTOR can update territories"),
            @ApiResponse(responseCode = "404", description = "Territory not found with provided ID"),
            @ApiResponse(responseCode = "409", description = "Conflict - Specified region not found"),
            @ApiResponse(responseCode = "500", description = "Server error while processing the request")
    })
    @SecurityRequirement(name = "bearer-jwt")
    public ResponseEntity<TerritoryResponseDTO> update(
            @Parameter(name = "id", description = "Unique territory identifier to update", example = "01001", required = true)
            @PathVariable String id,
            @Valid @RequestBody TerritoryUpdateRequestDTO dto) {
        return ResponseEntity.ok(territoryService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'GESTOR')")
    @Operation(summary = "Delete a territory", description = "Deletes a sales territory from the system. Only ADMIN and GESTOR roles can delete territories. Ensure no employees are assigned to this territory before deletion.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Territory deleted successfully - No content returned"),
            @ApiResponse(responseCode = "400", description = "Invalid territory ID format"),
            @ApiResponse(responseCode = "401", description = "Authentication required - Missing or invalid Bearer token"),
            @ApiResponse(responseCode = "403", description = "Insufficient permissions - Only ADMIN and GESTOR can delete territories"),
            @ApiResponse(responseCode = "404", description = "Territory not found with provided ID"),
            @ApiResponse(responseCode = "409", description = "Conflict - Territory cannot be deleted (employees still assigned)"),
            @ApiResponse(responseCode = "500", description = "Server error while processing the request")
    })
    @SecurityRequirement(name = "bearer-jwt")
    public ResponseEntity<Void> delete(
            @Parameter(name = "id", description = "Unique territory identifier to delete", example = "01001", required = true)
            @PathVariable String id) {
        territoryService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
