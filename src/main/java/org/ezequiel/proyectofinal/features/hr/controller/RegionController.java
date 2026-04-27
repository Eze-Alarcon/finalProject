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
import org.ezequiel.proyectofinal.features.hr.dto.RegionRequestDTO;
import org.ezequiel.proyectofinal.features.hr.dto.RegionResponseDTO;
import org.ezequiel.proyectofinal.features.hr.service.RegionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/regions")
@RequiredArgsConstructor
@Tag(name = "HR - Regions", description = "Geographic regions management. Regions represent defined geographic areas where employees manage sales and operations.")
public class RegionController {

    private final RegionService regionService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'GESTOR', 'CONSULTA')")
    @Operation(summary = "List all regions", description = "Retrieves a complete list of all geographic regions. Available to ADMIN, GESTOR, and CONSULTA roles.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of regions retrieved successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(type = "array", implementation = RegionResponseDTO.class))),
            @ApiResponse(responseCode = "401", description = "Authentication required - Missing or invalid Bearer token"),
            @ApiResponse(responseCode = "403", description = "Insufficient permissions - User role does not allow access"),
            @ApiResponse(responseCode = "500", description = "Server error while processing the request")
    })
    @SecurityRequirement(name = "bearer-jwt")
    public ResponseEntity<List<RegionResponseDTO>> findAll() {
        return ResponseEntity.ok(regionService.findAll());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'GESTOR', 'CONSULTA')")
    @Operation(summary = "Get region by ID", description = "Retrieves detailed information about a specific geographic region. Available to ADMIN, GESTOR, and CONSULTA roles.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Region retrieved successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = RegionResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid region ID format"),
            @ApiResponse(responseCode = "401", description = "Authentication required - Missing or invalid Bearer token"),
            @ApiResponse(responseCode = "403", description = "Insufficient permissions - User role does not allow access"),
            @ApiResponse(responseCode = "404", description = "Region not found with provided ID"),
            @ApiResponse(responseCode = "500", description = "Server error while processing the request")
    })
    @SecurityRequirement(name = "bearer-jwt")
    public ResponseEntity<RegionResponseDTO> findById(
            @Parameter(name = "id", description = "Unique region identifier", example = "1", required = true)
            @PathVariable Short id) {
        return ResponseEntity.ok(regionService.findById(id));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'GESTOR')")
    @Operation(summary = "Create a new region", description = "Creates a new geographic region. Only ADMIN and GESTOR roles are allowed to create regions.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Region created successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = RegionResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input - Region description is required or exceeds maximum length"),
            @ApiResponse(responseCode = "401", description = "Authentication required - Missing or invalid Bearer token"),
            @ApiResponse(responseCode = "403", description = "Insufficient permissions - Only ADMIN and GESTOR can create regions"),
            @ApiResponse(responseCode = "409", description = "Conflict - Region description already exists"),
            @ApiResponse(responseCode = "500", description = "Server error while processing the request")
    })
    @SecurityRequirement(name = "bearer-jwt")
    public ResponseEntity<RegionResponseDTO> save(@Valid @RequestBody RegionRequestDTO dto) {
        RegionResponseDTO created = regionService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'GESTOR')")
    @Operation(summary = "Update an existing region", description = "Updates the geographic region information. Only ADMIN and GESTOR roles can modify regions. Requires complete region data.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Region updated successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = RegionResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input - Region description is required or exceeds maximum length"),
            @ApiResponse(responseCode = "401", description = "Authentication required - Missing or invalid Bearer token"),
            @ApiResponse(responseCode = "403", description = "Insufficient permissions - Only ADMIN and GESTOR can update regions"),
            @ApiResponse(responseCode = "404", description = "Region not found with provided ID"),
            @ApiResponse(responseCode = "409", description = "Conflict - Region description already exists"),
            @ApiResponse(responseCode = "500", description = "Server error while processing the request")
    })
    @SecurityRequirement(name = "bearer-jwt")
    public ResponseEntity<RegionResponseDTO> update(
            @Parameter(name = "id", description = "Unique region identifier to update", example = "1", required = true)
            @PathVariable Short id,
            @Valid @RequestBody RegionRequestDTO dto) {
        return ResponseEntity.ok(regionService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'GESTOR')")
    @Operation(summary = "Delete a region", description = "Deletes a geographic region from the system. Only ADMIN and GESTOR roles can delete regions. Ensure no employees are assigned to this region before deletion.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Region deleted successfully - No content returned"),
            @ApiResponse(responseCode = "400", description = "Invalid region ID format"),
            @ApiResponse(responseCode = "401", description = "Authentication required - Missing or invalid Bearer token"),
            @ApiResponse(responseCode = "403", description = "Insufficient permissions - Only ADMIN and GESTOR can delete regions"),
            @ApiResponse(responseCode = "404", description = "Region not found with provided ID"),
            @ApiResponse(responseCode = "409", description = "Conflict - Region cannot be deleted (employees still assigned)"),
            @ApiResponse(responseCode = "500", description = "Server error while processing the request")
    })
    @SecurityRequirement(name = "bearer-jwt")
    public ResponseEntity<Void> delete(
            @Parameter(name = "id", description = "Unique region identifier to delete", example = "1", required = true)
            @PathVariable Short id) {
        regionService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
