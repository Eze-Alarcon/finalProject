package org.ezequiel.proyectofinal.features.catalog.service;

import org.ezequiel.proyectofinal.features.catalog.dto.CategoryRequestDTO;
import org.ezequiel.proyectofinal.features.catalog.dto.CategoryResponseDTO;

import java.util.List;

public interface CategoryService {

    List<CategoryResponseDTO> findAll();

    CategoryResponseDTO findById(Short id);

    CategoryResponseDTO save(CategoryRequestDTO dto);

    CategoryResponseDTO update(Short id, CategoryRequestDTO dto);

    void delete(Short id);
}
