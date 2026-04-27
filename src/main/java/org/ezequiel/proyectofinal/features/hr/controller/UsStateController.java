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
@Tag(name = "HR - US States", description = "United States reference data management. US States represent geographical and administrative divisions used for business operations, customer locations, and territory organization.")
public class UsStateController {

    private final UsStateService usStateService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'GESTOR', 'CONSULTA')")
    @Operation(summary = "List all US states", description = "Retrieves a complete list of all US states with their identifiers, abbreviations, and regional information. Available to ADMIN, GESTOR, and CONSULTA roles.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of US states retrieved successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(type = "array", implementation = UsStateResponseDTO.class))),
            @ApiResponse(responseCode = "401", description = "Authentication required - Missing or invalid Bearer token"),
            @ApiResponse(responseCode = "403", description = "Insufficient permissions - User role does not allow access"),
            @ApiResponse(responseCode = "500", description = "Server error while processing the request")
    })
    @SecurityRequirement(name = "bearer-jwt")
    public ResponseEntity<List<UsStateResponseDTO>> findAll() {
        return ResponseEntity.ok(usStateService.findAll());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'GESTOR', 'CONSULTA')")
    @Operation(summary = "Get US state by ID", description = "Retrieves detailed information about a specific US state including its abbreviation and regional classification. Available to ADMIN, GESTOR, and CONSULTA roles.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "US state retrieved successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UsStateResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid state ID format"),
            @ApiResponse(responseCode = "401", description = "Authentication required - Missing or invalid Bearer token"),
            @ApiResponse(responseCode = "403", description = "Insufficient permissions - User role does not allow access"),
            @ApiResponse(responseCode = "404", description = "US state not found with provided ID"),
            @ApiResponse(responseCode = "500", description = "Server error while processing the request")
    })
    @SecurityRequirement(name = "bearer-jwt")
    public ResponseEntity<UsStateResponseDTO> findById(
            @Parameter(name = "id", description = "Unique US state identifier", example = "1", required = true)
            @PathVariable Short id) {
        return ResponseEntity.ok(usStateService.findById(id));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'GESTOR')")
    @Operation(summary = "Create a new US state", description = "Creates a new US state reference entry in the system. Only ADMIN and GESTOR roles are allowed to create states. State information must be complete and valid.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "US state created successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UsStateResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input - State data exceeds maximum length constraints"),
            @ApiResponse(responseCode = "401", description = "Authentication required - Missing or invalid Bearer token"),
            @ApiResponse(responseCode = "403", description = "Insufficient permissions - Only ADMIN and GESTOR can create states"),
            @ApiResponse(responseCode = "409", description = "Conflict - State already exists or duplicate abbreviation"),
            @ApiResponse(responseCode = "500", description = "Server error while processing the request")
    })
    @SecurityRequirement(name = "bearer-jwt")
    public ResponseEntity<UsStateResponseDTO> save(@Valid @RequestBody UsStateRequestDTO dto) {
        UsStateResponseDTO created = usStateService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'GESTOR')")
    @Operation(summary = "Update an existing US state", description = "Updates the US state reference information including name, abbreviation, and regional classification. Only ADMIN and GESTOR roles can modify states. Requires complete state data.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "US state updated successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UsStateResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input - State data exceeds maximum length constraints"),
            @ApiResponse(responseCode = "401", description = "Authentication required - Missing or invalid Bearer token"),
            @ApiResponse(responseCode = "403", description = "Insufficient permissions - Only ADMIN and GESTOR can update states"),
            @ApiResponse(responseCode = "404", description = "US state not found with provided ID"),
            @ApiResponse(responseCode = "409", description = "Conflict - Duplicate abbreviation with another state"),
            @ApiResponse(responseCode = "500", description = "Server error while processing the request")
    })
    @SecurityRequirement(name = "bearer-jwt")
    public ResponseEntity<UsStateResponseDTO> update(
            @Parameter(name = "id", description = "Unique US state identifier to update", example = "1", required = true)
            @PathVariable Short id,
            @Valid @RequestBody UsStateRequestDTO dto) {
        return ResponseEntity.ok(usStateService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'GESTOR')")
    @Operation(summary = "Delete a US state", description = "Deletes a US state from the system. Only ADMIN and GESTOR roles can delete states. Ensure no territories or locations reference this state before deletion.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "US state deleted successfully - No content returned"),
            @ApiResponse(responseCode = "400", description = "Invalid state ID format"),
            @ApiResponse(responseCode = "401", description = "Authentication required - Missing or invalid Bearer token"),
            @ApiResponse(responseCode = "403", description = "Insufficient permissions - Only ADMIN and GESTOR can delete states"),
            @ApiResponse(responseCode = "404", description = "US state not found with provided ID"),
            @ApiResponse(responseCode = "409", description = "Conflict - State cannot be deleted (locations/territories still reference it)"),
            @ApiResponse(responseCode = "500", description = "Server error while processing the request")
    })
    @SecurityRequirement(name = "bearer-jwt")
    public ResponseEntity<Void> delete(
            @Parameter(name = "id", description = "Unique US state identifier to delete", example = "1", required = true)
            @PathVariable Short id) {
        usStateService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
