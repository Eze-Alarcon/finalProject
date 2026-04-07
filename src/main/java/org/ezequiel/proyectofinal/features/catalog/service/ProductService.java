package org.ezequiel.proyectofinal.features.catalog.service;

import org.ezequiel.proyectofinal.features.catalog.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {

    List<ProductResponseDTO> findAll();

    ProductResponseDTO findById(Short id);

    ProductResponseDTO save(ProductRequestDTO dto);

    ProductResponseDTO update(Short id, ProductUpdateRequestDTO dto);

    ProductResponseDTO restock(Short id, RestockRequestDTO dto);

    ProductResponseDTO discontinue(Short id);

    Page<ProductResponseDTO> search(Short categoryId, Short supplierId, Float minPrice, Float maxPrice, Boolean inStock, Pageable pageable);

    List<ProductLowStockResponseDTO> getLowStockAlerts();

    void delete(Short id);
}
