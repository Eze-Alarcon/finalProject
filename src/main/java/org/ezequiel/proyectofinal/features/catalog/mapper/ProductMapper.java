package org.ezequiel.proyectofinal.features.catalog.mapper;

import org.ezequiel.proyectofinal.features.catalog.dto.ProductRequestDTO;
import org.ezequiel.proyectofinal.features.catalog.dto.ProductResponseDTO;
import org.ezequiel.proyectofinal.features.catalog.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(target = "category", ignore = true)
    Product toEntity(ProductRequestDTO dto);

    @Mapping(target = "categoryId", source = "category.categoryId")
    @Mapping(target = "categoryName", source = "category.categoryName")
    ProductResponseDTO toResponseDTO(Product entity);

    @Mapping(target = "categoryId", source = "category.categoryId")
    ProductRequestDTO toRequestDTO(Product entity);

    @Mapping(target = "category", ignore = true)
    void updateEntityFromDTO(ProductRequestDTO dto, @MappingTarget Product entity);
}
