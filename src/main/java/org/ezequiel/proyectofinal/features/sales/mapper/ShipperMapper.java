package org.ezequiel.proyectofinal.features.sales.mapper;

import org.ezequiel.proyectofinal.features.sales.dto.ShipperRequestDTO;
import org.ezequiel.proyectofinal.features.sales.dto.ShipperResponseDTO;
import org.ezequiel.proyectofinal.features.sales.entity.Shipper;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface ShipperMapper {

    Shipper toEntity(ShipperRequestDTO dto);

    ShipperResponseDTO toResponseDTO(Shipper entity);

    ShipperRequestDTO toRequestDTO(Shipper entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDTO(ShipperRequestDTO dto, @MappingTarget Shipper entity);
}
