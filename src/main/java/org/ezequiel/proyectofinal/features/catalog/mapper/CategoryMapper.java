package org.ezequiel.proyectofinal.features.catalog.mapper;

import org.ezequiel.proyectofinal.features.catalog.dto.CategoryRequestDTO;
import org.ezequiel.proyectofinal.features.catalog.dto.CategoryResponseDTO;
import org.ezequiel.proyectofinal.features.catalog.entity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    Category toEntity(CategoryRequestDTO dto);

    CategoryResponseDTO toResponseDTO(Category entity);

    CategoryRequestDTO toRequestDTO(Category entity);

    @Mapping(target = "products", ignore = true)
    void updateEntityFromDTO(CategoryRequestDTO dto, @MappingTarget Category entity);
}
