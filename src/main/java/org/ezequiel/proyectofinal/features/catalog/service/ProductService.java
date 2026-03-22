package org.ezequiel.proyectofinal.features.catalog.service;

import org.ezequiel.proyectofinal.features.catalog.dto.ProductRequestDTO;
import org.ezequiel.proyectofinal.features.catalog.dto.ProductResponseDTO;

import java.util.List;

public interface ProductService {

    List<ProductResponseDTO> findAll();

    ProductResponseDTO findById(Short id);

    ProductResponseDTO save(ProductRequestDTO dto);

    ProductResponseDTO update(Short id, ProductRequestDTO dto);

    void delete(Short id);
}
