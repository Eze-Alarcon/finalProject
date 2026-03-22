package org.ezequiel.proyectofinal.features.catalog.mapper;

import org.ezequiel.proyectofinal.features.catalog.dto.SupplierRequestDTO;
import org.ezequiel.proyectofinal.features.catalog.dto.SupplierResponseDTO;
import org.ezequiel.proyectofinal.features.catalog.entity.Supplier;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface SupplierMapper {

    @Mapping(target = "products", ignore = true)
    Supplier toEntity(SupplierRequestDTO dto);

    SupplierResponseDTO toResponseDTO(Supplier entity);

    SupplierRequestDTO toRequestDTO(Supplier entity);

    @Mapping(target = "products", ignore = true)
    void updateEntityFromDTO(SupplierRequestDTO dto, @MappingTarget Supplier entity);
}
