package org.ezequiel.proyectofinal.features.hr.mapper;

import org.ezequiel.proyectofinal.features.hr.dto.RegionRequestDTO;
import org.ezequiel.proyectofinal.features.hr.dto.RegionResponseDTO;
import org.ezequiel.proyectofinal.features.hr.entity.Region;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface RegionMapper {

    @Mapping(target = "territories", ignore = true)
    Region toEntity(RegionRequestDTO dto);

    RegionResponseDTO toResponseDTO(Region entity);

    RegionRequestDTO toRequestDTO(Region entity);

    @Mapping(target = "territories", ignore = true)
    void updateEntityFromDTO(RegionRequestDTO dto, @MappingTarget Region entity);
}
