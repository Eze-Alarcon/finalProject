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
import org.ezequiel.proyectofinal.features.catalog.dto.ProductRequestDTO;
import org.ezequiel.proyectofinal.features.catalog.dto.ProductResponseDTO;
import org.ezequiel.proyectofinal.features.catalog.dto.ProductUpdateRequestDTO;
import org.ezequiel.proyectofinal.features.catalog.dto.RestockRequestDTO;
import org.ezequiel.proyectofinal.features.catalog.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
@Tag(name = "Catalog - Products", description = "API de gestión de productos del catálogo. Permite consultar, crear, actualizar, reabastecer y dar de baja productos, manteniendo referencias explícitas a categoría y proveedor cuando están presentes en la respuesta o en la entrada.")
public class ProductController {

    private final ProductService productService;

        @Operation(
            summary = "Buscar productos",
            description = "Recupera una página de productos aplicando filtros opcionales por categoría, proveedor, rango de precio y disponibilidad en stock. Disponible para roles de consulta y gestión.",
            security = @SecurityRequirement(name = "bearer")
        )
        @ApiResponses({
            @ApiResponse(
                responseCode = "200",
                description = "Página de productos recuperada exitosamente",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ProductResponseDTO.class)
                )
            ),
            @ApiResponse(responseCode = "401", description = "Token de autenticación no válido o ausente", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "403", description = "Sin permisos suficientes - Se requiere rol ADMIN, WAREHOUSE, GESTOR o CONSULTA", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(mediaType = "application/json"))
        })
        @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'WAREHOUSE', 'GESTOR', 'CONSULTA')")
    public ResponseEntity<Page<ProductResponseDTO>> search(
            @Parameter(description = "Filtra por ID de categoría", example = "1")
            @RequestParam(required = false) Short categoryId,
            @Parameter(description = "Filtra por ID de proveedor", example = "3")
            @RequestParam(required = false) Short supplierId,
            @Parameter(description = "Precio mínimo del producto a considerar", example = "5.0")
            @RequestParam(required = false) Float minPrice,
            @Parameter(description = "Precio máximo del producto a considerar", example = "20.0")
            @RequestParam(required = false) Float maxPrice,
            @Parameter(description = "Indica si solo deben incluirse productos con stock disponible", example = "true")
            @RequestParam(required = false) Boolean inStock,
            Pageable pageable) {
        return ResponseEntity.ok(productService.search(categoryId, supplierId, minPrice, maxPrice, inStock, pageable));
    }

    // TODO: quitar este endpoint
    @Deprecated
        @Operation(
            summary = "Listar todos los productos (deprecated)",
            description = "Devuelve la lista completa de productos sin aplicar filtros ni paginación. Este endpoint está marcado como obsoleto y debe sustituirse por la búsqueda principal en GET /api/v1/products.",
            security = @SecurityRequirement(name = "bearer")
        )
        @ApiResponses({
            @ApiResponse(
                responseCode = "200",
                description = "Lista completa de productos recuperada exitosamente",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ProductResponseDTO.class)
                )
            ),
            @ApiResponse(responseCode = "401", description = "Token de autenticación no válido o ausente", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "403", description = "Sin permisos suficientes - Se requiere rol ADMIN, WAREHOUSE, GESTOR o CONSULTA", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(mediaType = "application/json"))
        })
        @GetMapping("/search")
    @PreAuthorize("hasAnyRole('ADMIN', 'WAREHOUSE', 'GESTOR', 'CONSULTA')")
    public ResponseEntity<List<ProductResponseDTO>> findAll() {
        return ResponseEntity.ok(productService.findAll());
    }

        @Operation(
            summary = "Obtener producto por ID",
            description = "Recupera la información detallada de un producto específico mediante su identificador numérico. La respuesta incluye referencias explícitas a categoría y proveedor cuando existen en el modelo de salida.",
            security = @SecurityRequirement(name = "bearer")
        )
        @ApiResponses({
            @ApiResponse(
                responseCode = "200",
                description = "Producto encontrado y recuperado exitosamente",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ProductResponseDTO.class)
                )
            ),
            @ApiResponse(responseCode = "401", description = "Token de autenticación no válido o ausente", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "403", description = "Sin permisos suficientes - Se requiere rol ADMIN, WAREHOUSE, GESTOR o CONSULTA", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(mediaType = "application/json"))
        })
        @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'WAREHOUSE', 'GESTOR', 'CONSULTA')")
        public ResponseEntity<ProductResponseDTO> findById(
            @Parameter(description = "ID único del producto a consultar", required = true, example = "1")
            @PathVariable Short id) {
        return ResponseEntity.ok(productService.findById(id));
    }

        @Operation(
            summary = "Crear nuevo producto",
            description = "Registra un nuevo producto en el catálogo. Solo usuarios con permisos administrativos o de almacén pueden crear productos. La entrada permite asociar el producto a una categoría y un proveedor mediante sus identificadores, cuando corresponda.",
            security = @SecurityRequirement(name = "bearer")
        )
        @ApiResponses({
            @ApiResponse(
                responseCode = "201",
                description = "Producto creado exitosamente",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ProductResponseDTO.class)
                )
            ),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "401", description = "Token de autenticación no válido o ausente", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "403", description = "Sin permisos suficientes - Se requiere rol ADMIN o WAREHOUSE", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(mediaType = "application/json"))
        })
        @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'WAREHOUSE')")
        public ResponseEntity<ProductResponseDTO> save(
            @RequestBody(
                description = "Datos del nuevo producto a registrar",
                required = true,
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ProductRequestDTO.class),
                    examples = @ExampleObject(
                        name = "ProductCreateExample",
                        summary = "Producto de ejemplo",
                        value = """
                            {
                              "productName": "Chai",
                              "supplierId": 3,
                              "categoryId": 1,
                              "quantityPerUnit": "10 boxes x 20 bags",
                              "unitPrice": 18.0,
                              "unitsInStock": 39,
                              "reorderLevel": 10
                            }
                            """
                    )
                )
            )
            @Valid @org.springframework.web.bind.annotation.RequestBody ProductRequestDTO dto) {
        ProductResponseDTO created = productService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

        @Operation(
            summary = "Actualizar producto",
            description = "Modifica los datos de un producto existente. Soporta actualización mediante PUT y PATCH con el mismo contrato de entrada. Solo usuarios con permisos administrativos o de almacén pueden actualizar productos.",
            security = @SecurityRequirement(name = "bearer")
        )
        @ApiResponses({
            @ApiResponse(
                responseCode = "200",
                description = "Producto actualizado exitosamente",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ProductResponseDTO.class)
                )
            ),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "401", description = "Token de autenticación no válido o ausente", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "403", description = "Sin permisos suficientes - Se requiere rol ADMIN o WAREHOUSE", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(mediaType = "application/json"))
        })
        @PatchMapping("/{id}")
        @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'WAREHOUSE')")
    public ResponseEntity<ProductResponseDTO> update(
            @Parameter(description = "ID único del producto a actualizar", required = true, example = "1")
            @PathVariable Short id,
            @RequestBody(
                description = "Datos actualizados del producto",
                required = true,
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ProductUpdateRequestDTO.class),
                    examples = @ExampleObject(
                        name = "ProductUpdateExample",
                        summary = "Actualización de producto",
                        value = """
                            {
                              "productName": "Chai Premium",
                              "supplierId": 3,
                              "categoryId": 1,
                              "quantityPerUnit": "10 boxes x 20 bags",
                              "unitPrice": 19.5,
                              "reorderLevel": 12
                            }
                            """
                    )
                )
            )
            @org.springframework.web.bind.annotation.RequestBody ProductUpdateRequestDTO dto) {
        return ResponseEntity.ok(productService.update(id, dto));
    }

        @Operation(
            summary = "Reabastecer producto",
            description = "Incrementa la cantidad disponible de un producto existente. Esta operación utiliza un payload mínimo con la cantidad a sumar y solo está disponible para roles administrativos o de almacén.",
            security = @SecurityRequirement(name = "bearer")
        )
        @ApiResponses({
            @ApiResponse(
                responseCode = "200",
                description = "Producto reabastecido exitosamente",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ProductResponseDTO.class)
                )
            ),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "401", description = "Token de autenticación no válido o ausente", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "403", description = "Sin permisos suficientes - Se requiere rol ADMIN o WAREHOUSE", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(mediaType = "application/json"))
        })
        @PatchMapping("/{id}/restock")
    @PreAuthorize("hasAnyRole('ADMIN', 'WAREHOUSE')")
    public ResponseEntity<ProductResponseDTO> restock(
            @Parameter(description = "ID único del producto a reabastecer", required = true, example = "1")
            @PathVariable Short id,
            @RequestBody(
                description = "Cantidad a sumar al inventario del producto",
                required = true,
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = RestockRequestDTO.class),
                    examples = @ExampleObject(
                        name = "RestockExample",
                        summary = "Reabastecimiento de ejemplo",
                        value = """
                            {
                              "quantity": 25
                            }
                            """
                    )
                )
            )
            @Valid @org.springframework.web.bind.annotation.RequestBody RestockRequestDTO dto) {
        return ResponseEntity.ok(productService.restock(id, dto));
    }

        @Operation(
            summary = "Dar de baja producto",
            description = "Marca un producto como descontinuado mediante la lógica definida en el servicio. Solo usuarios con permisos administrativos o de almacén pueden ejecutar esta acción.",
            security = @SecurityRequirement(name = "bearer")
        )
        @ApiResponses({
            @ApiResponse(
                responseCode = "200",
                description = "Producto dado de baja exitosamente",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ProductResponseDTO.class)
                )
            ),
            @ApiResponse(responseCode = "401", description = "Token de autenticación no válido o ausente", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "403", description = "Sin permisos suficientes - Se requiere rol ADMIN o WAREHOUSE", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(mediaType = "application/json"))
        })
        @PatchMapping("/{id}/discontinue")
    @PreAuthorize("hasAnyRole('ADMIN', 'WAREHOUSE')")
        public ResponseEntity<ProductResponseDTO> discontinue(
            @Parameter(description = "ID único del producto a descontinuar", required = true, example = "1")
            @PathVariable Short id) {
        return ResponseEntity.ok(productService.discontinue(id));
    }

        @Operation(
            summary = "Eliminar producto",
            description = "Elimina permanentemente un producto del catálogo. Esta operación es irreversible y solo puede ser realizada por roles administrativos o de almacén.",
            security = @SecurityRequirement(name = "bearer")
        )
        @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Producto eliminado exitosamente"),
            @ApiResponse(responseCode = "401", description = "Token de autenticación no válido o ausente", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "403", description = "Sin permisos suficientes - Se requiere rol ADMIN o WAREHOUSE", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(mediaType = "application/json"))
        })
        @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'WAREHOUSE')")
        public ResponseEntity<Void> delete(
            @Parameter(description = "ID único del producto a eliminar", required = true, example = "1")
            @PathVariable Short id) {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
