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
import org.ezequiel.proyectofinal.features.catalog.dto.CategoryRequestDTO;
import org.ezequiel.proyectofinal.features.catalog.dto.CategoryResponseDTO;
import org.ezequiel.proyectofinal.features.catalog.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
@Tag(name = "Catalog - Categories", description = "API de gestión de categorías de productos del catálogo. Permite consultar, crear, actualizar y eliminar categorías simples sin asumir jerarquías ni subcategorías adicionales.")
public class CategoryController {

    private final CategoryService categoryService;

        @Operation(
            summary = "Obtener todas las categorías",
            description = "Recupera la lista completa de categorías de productos disponibles en el catálogo. Disponible para roles de consulta y gestión.",
            security = @SecurityRequirement(name = "bearer")
        )
        @ApiResponses({
            @ApiResponse(
                responseCode = "200",
                description = "Lista de categorías recuperada exitosamente",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(type = "array", implementation = CategoryResponseDTO.class)
                )
            ),
            @ApiResponse(responseCode = "401", description = "Token de autenticación no válido o ausente", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "403", description = "Sin permisos suficientes - Se requiere rol ADMIN, WAREHOUSE, GESTOR o CONSULTA", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(mediaType = "application/json"))
        })
        @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'WAREHOUSE', 'GESTOR', 'CONSULTA')")
    public ResponseEntity<List<CategoryResponseDTO>> findAll() {
        return ResponseEntity.ok(categoryService.findAll());
    }

        @Operation(
            summary = "Obtener categoría por ID",
            description = "Recupera la información detallada de una categoría de producto mediante su identificador numérico. Disponible para roles de consulta y gestión.",
            security = @SecurityRequirement(name = "bearer")
        )
        @ApiResponses({
            @ApiResponse(
                responseCode = "200",
                description = "Categoría encontrada y recuperada exitosamente",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = CategoryResponseDTO.class)
                )
            ),
            @ApiResponse(responseCode = "401", description = "Token de autenticación no válido o ausente", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "403", description = "Sin permisos suficientes - Se requiere rol ADMIN, WAREHOUSE, GESTOR o CONSULTA", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Categoría no encontrada", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(mediaType = "application/json"))
        })
        @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'WAREHOUSE', 'GESTOR', 'CONSULTA')")
        public ResponseEntity<CategoryResponseDTO> findById(
            @Parameter(description = "ID único de la categoría a consultar", required = true, example = "1")
            @PathVariable Short id) {
        return ResponseEntity.ok(categoryService.findById(id));
    }

        @Operation(
            summary = "Crear nueva categoría",
            description = "Registra una nueva categoría en el catálogo. Solo usuarios con permisos administrativos o de almacén pueden crear categorías. El nombre de la categoría debe ser único dentro del dominio de la aplicación.",
            security = @SecurityRequirement(name = "bearer")
        )
        @ApiResponses({
            @ApiResponse(
                responseCode = "201",
                description = "Categoría creada exitosamente",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = CategoryResponseDTO.class)
                )
            ),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "401", description = "Token de autenticación no válido o ausente", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "403", description = "Sin permisos suficientes - Se requiere rol ADMIN o WAREHOUSE", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(mediaType = "application/json"))
        })
        @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'WAREHOUSE')")
        public ResponseEntity<CategoryResponseDTO> save(
            @RequestBody(
                description = "Datos de la nueva categoría a registrar",
                required = true,
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = CategoryRequestDTO.class),
                    examples = @ExampleObject(
                        name = "CategoryCreateExample",
                        summary = "Categoría de ejemplo",
                        value = """
                            {
                              "categoryName": "Beverages",
                              "description": "Productos de bebidas y refrescos",
                              "picture": null
                            }
                            """
                    )
                )
            )
            @Valid @org.springframework.web.bind.annotation.RequestBody CategoryRequestDTO dto) {
        CategoryResponseDTO created = categoryService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

        @Operation(
            summary = "Actualizar categoría",
            description = "Modifica los datos de una categoría existente. Soporta actualización mediante PUT y PATCH con el mismo contrato de entrada. Solo usuarios con permisos administrativos o de almacén pueden actualizar categorías.",
            security = @SecurityRequirement(name = "bearer")
        )
        @ApiResponses({
            @ApiResponse(
                responseCode = "200",
                description = "Categoría actualizada exitosamente",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = CategoryResponseDTO.class)
                )
            ),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "401", description = "Token de autenticación no válido o ausente", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "403", description = "Sin permisos suficientes - Se requiere rol ADMIN o WAREHOUSE", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Categoría no encontrada", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(mediaType = "application/json"))
        })
        @PatchMapping("/{id}")
        @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'WAREHOUSE')")
    public ResponseEntity<CategoryResponseDTO> update(
            @Parameter(description = "ID único de la categoría a actualizar", required = true, example = "1")
            @PathVariable Short id,
            @RequestBody(
                description = "Datos actualizados de la categoría",
                required = true,
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = CategoryRequestDTO.class),
                    examples = @ExampleObject(
                        name = "CategoryUpdateExample",
                        summary = "Actualización de categoría",
                        value = """
                            {
                              "categoryName": "Beverages",
                              "description": "Bebidas, refrescos y otros líquidos de consumo",
                              "picture": null
                            }
                            """
                    )
                )
            )
            @org.springframework.web.bind.annotation.RequestBody CategoryRequestDTO dto) {
        return ResponseEntity.ok(categoryService.update(id, dto));
    }

        @Operation(
            summary = "Eliminar categoría",
            description = "Elimina de forma permanente una categoría del catálogo. Esta operación no modifica relaciones por sí misma y solo puede ser ejecutada por roles administrativos o de almacén.",
            security = @SecurityRequirement(name = "bearer")
        )
        @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Categoría eliminada exitosamente"),
            @ApiResponse(responseCode = "401", description = "Token de autenticación no válido o ausente", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "403", description = "Sin permisos suficientes - Se requiere rol ADMIN o WAREHOUSE", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Categoría no encontrada", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(mediaType = "application/json"))
        })
        @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'WAREHOUSE')")
        public ResponseEntity<Void> delete(
            @Parameter(description = "ID único de la categoría a eliminar", required = true, example = "1")
            @PathVariable Short id) {
        categoryService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
