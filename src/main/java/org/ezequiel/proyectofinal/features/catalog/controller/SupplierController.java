package org.ezequiel.proyectofinal.features.catalog.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.ezequiel.proyectofinal.features.catalog.dto.SupplierRequestDTO;
import org.ezequiel.proyectofinal.features.catalog.dto.SupplierResponseDTO;
import org.ezequiel.proyectofinal.features.catalog.service.SupplierService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/suppliers")
@RequiredArgsConstructor
@Tag(name = "Catalog - Suppliers", description = "API de gestión de proveedores del catálogo. Permite consultar, crear, actualizar y eliminar proveedores con una estructura empresarial simple y sin asumir procesos externos adicionales.")
public class SupplierController {

    private final SupplierService supplierService;

        @Operation(
            summary = "Obtener todos los proveedores",
            description = "Recupera la lista completa de proveedores registrados en el catálogo. Disponible para roles de consulta y gestión.",
            security = @SecurityRequirement(name = "bearer")
        )
        @ApiResponses({
            @ApiResponse(
                responseCode = "200",
                description = "Lista de proveedores recuperada exitosamente",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(type = "array", implementation = SupplierResponseDTO.class)
                )
            ),
            @ApiResponse(responseCode = "401", description = "Token de autenticación no válido o ausente", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "403", description = "Sin permisos suficientes - Se requiere rol ADMIN, WAREHOUSE, GESTOR o CONSULTA", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(mediaType = "application/json"))
        })
        @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'WAREHOUSE', 'GESTOR', 'CONSULTA')")
    public ResponseEntity<List<SupplierResponseDTO>> findAll() {
        return ResponseEntity.ok(supplierService.findAll());
    }

        @Operation(
            summary = "Obtener proveedor por ID",
            description = "Recupera la información detallada de un proveedor específico mediante su identificador numérico. La respuesta expone únicamente los datos del proveedor disponibles en el modelo actual.",
            security = @SecurityRequirement(name = "bearer")
        )
        @ApiResponses({
            @ApiResponse(
                responseCode = "200",
                description = "Proveedor encontrado y recuperado exitosamente",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = SupplierResponseDTO.class)
                )
            ),
            @ApiResponse(responseCode = "401", description = "Token de autenticación no válido o ausente", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "403", description = "Sin permisos suficientes - Se requiere rol ADMIN, WAREHOUSE, GESTOR o CONSULTA", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Proveedor no encontrado", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(mediaType = "application/json"))
        })
        @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'WAREHOUSE', 'GESTOR', 'CONSULTA')")
        public ResponseEntity<SupplierResponseDTO> findById(
            @Parameter(description = "ID único del proveedor a consultar", required = true, example = "3")
            @PathVariable Short id) {
        return ResponseEntity.ok(supplierService.findById(id));
    }

        @Operation(
            summary = "Crear nuevo proveedor",
            description = "Registra un nuevo proveedor en el catálogo. Solo usuarios con permisos administrativos o de almacén pueden crear proveedores. Los datos de contacto y dirección son opcionales salvo el nombre de la compañía.",
            security = @SecurityRequirement(name = "bearer")
        )
        @ApiResponses({
            @ApiResponse(
                responseCode = "201",
                description = "Proveedor creado exitosamente",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = SupplierResponseDTO.class)
                )
            ),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "401", description = "Token de autenticación no válido o ausente", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "403", description = "Sin permisos suficientes - Se requiere rol ADMIN o WAREHOUSE", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(mediaType = "application/json"))
        })
        @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'WAREHOUSE')")
        public ResponseEntity<SupplierResponseDTO> save(
            @RequestBody(
                description = "Datos del nuevo proveedor a registrar",
                required = true,
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = SupplierRequestDTO.class),
                    examples = @ExampleObject(
                        name = "SupplierCreateExample",
                        summary = "Proveedor de ejemplo",
                        value = """
                            {
                              "companyName": "Exotic Liquids",
                              "contactName": "Charlotte Cooper",
                              "contactTitle": "Purchasing Manager",
                              "address": "49 Gilbert St.",
                              "city": "London",
                              "region": null,
                              "postalCode": "EC1 4SD",
                              "country": "UK",
                              "phone": "(171) 555-2222",
                              "fax": null,
                              "homepage": "http://exoticliquids.example.com"
                            }
                            """
                    )
                )
            )
            @Valid @org.springframework.web.bind.annotation.RequestBody SupplierRequestDTO dto) {
        SupplierResponseDTO created = supplierService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

        @Operation(
            summary = "Actualizar proveedor",
            description = "Modifica los datos de un proveedor existente. Soporta actualización mediante PUT y PATCH con el mismo contrato de entrada. Solo usuarios con permisos administrativos o de almacén pueden actualizar proveedores.",
            security = @SecurityRequirement(name = "bearer")
        )
        @ApiResponses({
            @ApiResponse(
                responseCode = "200",
                description = "Proveedor actualizado exitosamente",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = SupplierResponseDTO.class)
                )
            ),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "401", description = "Token de autenticación no válido o ausente", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "403", description = "Sin permisos suficientes - Se requiere rol ADMIN o WAREHOUSE", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Proveedor no encontrado", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(mediaType = "application/json"))
        })
        @PatchMapping("/{id}")
        @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'WAREHOUSE')")
    public ResponseEntity<SupplierResponseDTO> update(
            @Parameter(description = "ID único del proveedor a actualizar", required = true, example = "3")
            @PathVariable Short id,
            @RequestBody(
                description = "Datos actualizados del proveedor",
                required = true,
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = SupplierRequestDTO.class),
                    examples = @ExampleObject(
                        name = "SupplierUpdateExample",
                        summary = "Actualización de proveedor",
                        value = """
                            {
                              "companyName": "Exotic Liquids Ltd.",
                              "contactName": "Charlotte Cooper",
                              "contactTitle": "Purchasing Manager",
                              "address": "49 Gilbert St.",
                              "city": "London",
                              "region": "Greater London",
                              "postalCode": "EC1 4SD",
                              "country": "UK",
                              "phone": "(171) 555-2222",
                              "fax": null,
                              "homepage": "http://exoticliquids.example.com"
                            }
                            """
                    )
                )
            )
            @org.springframework.web.bind.annotation.RequestBody SupplierRequestDTO dto) {
        return ResponseEntity.ok(supplierService.update(id, dto));
    }

        @Operation(
            summary = "Eliminar proveedor",
            description = "Elimina permanentemente un proveedor del catálogo. Esta operación es irreversible y solo puede ser realizada por roles administrativos o de almacén.",
            security = @SecurityRequirement(name = "bearer")
        )
        @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Proveedor eliminado exitosamente"),
            @ApiResponse(responseCode = "401", description = "Token de autenticación no válido o ausente", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "403", description = "Sin permisos suficientes - Se requiere rol ADMIN o WAREHOUSE", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Proveedor no encontrado", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(mediaType = "application/json"))
        })
        @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'WAREHOUSE')")
        public ResponseEntity<Void> delete(
            @Parameter(description = "ID único del proveedor a eliminar", required = true, example = "3")
            @PathVariable Short id) {
        supplierService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
