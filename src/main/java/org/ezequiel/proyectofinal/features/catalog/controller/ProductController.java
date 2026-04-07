package org.ezequiel.proyectofinal.features.catalog.controller;

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
public class ProductController {

    private final ProductService productService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'WAREHOUSE', 'GESTOR', 'CONSULTA')")
    public ResponseEntity<Page<ProductResponseDTO>> search(
            @RequestParam(required = false) Short categoryId,
            @RequestParam(required = false) Short supplierId,
            @RequestParam(required = false) Float minPrice,
            @RequestParam(required = false) Float maxPrice,
            @RequestParam(required = false) Boolean inStock,
            Pageable pageable) {
        return ResponseEntity.ok(productService.search(categoryId, supplierId, minPrice, maxPrice, inStock, pageable));
    }

    // TODO: quitar este endpoint
    @Deprecated
    @GetMapping("/search")
    @PreAuthorize("hasAnyRole('ADMIN', 'WAREHOUSE', 'GESTOR', 'CONSULTA')")
    public ResponseEntity<List<ProductResponseDTO>> findAll() {
        return ResponseEntity.ok(productService.findAll());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'WAREHOUSE', 'GESTOR', 'CONSULTA')")
    public ResponseEntity<ProductResponseDTO> findById(@PathVariable Short id) {
        return ResponseEntity.ok(productService.findById(id));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'WAREHOUSE')")
    public ResponseEntity<ProductResponseDTO> save(@Valid @RequestBody ProductRequestDTO dto) {
        ProductResponseDTO created = productService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PatchMapping("/{id}")
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'WAREHOUSE')")
    public ResponseEntity<ProductResponseDTO> update(
            @PathVariable Short id,
            @RequestBody ProductUpdateRequestDTO dto) {
        return ResponseEntity.ok(productService.update(id, dto));
    }

    @PatchMapping("/{id}/restock")
    @PreAuthorize("hasAnyRole('ADMIN', 'WAREHOUSE')")
    public ResponseEntity<ProductResponseDTO> restock(
            @PathVariable Short id,
            @Valid @RequestBody RestockRequestDTO dto) {
        return ResponseEntity.ok(productService.restock(id, dto));
    }

    @PatchMapping("/{id}/discontinue")
    @PreAuthorize("hasAnyRole('ADMIN', 'WAREHOUSE')")
    public ResponseEntity<ProductResponseDTO> discontinue(@PathVariable Short id) {
        return ResponseEntity.ok(productService.discontinue(id));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'WAREHOUSE')")
    public ResponseEntity<Void> delete(@PathVariable Short id) {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
