package org.ezequiel.proyectofinal.features.catalog.mapper;

import org.ezequiel.proyectofinal.features.catalog.dto.ProductRequestDTO;
import org.ezequiel.proyectofinal.features.catalog.dto.ProductResponseDTO;
import org.ezequiel.proyectofinal.features.catalog.dto.ProductUpdateRequestDTO;
import org.ezequiel.proyectofinal.features.catalog.entity.Product;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(target = "productId", ignore = true)
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "supplier", ignore = true)
    Product toEntity(ProductRequestDTO dto);

    @Mapping(target = "categoryId", source = "category.categoryId")
    @Mapping(target = "categoryName", source = "category.categoryName")
    @Mapping(target = "supplierId", source = "supplier.supplierId")
    @Mapping(target = "supplierName", source = "supplier.companyName")
    ProductResponseDTO toResponseDTO(Product entity);

    @Mapping(target = "categoryId", source = "category.categoryId")
    @Mapping(target = "supplierId", source = "supplier.supplierId")
    ProductRequestDTO toRequestDTO(Product entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "productId", ignore = true)
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "supplier", ignore = true)
    void updateEntityFromDTO(ProductUpdateRequestDTO dto, @MappingTarget Product entity);
}
