package org.ezequiel.proyectofinal.features.hr.mapper;

import org.ezequiel.proyectofinal.features.hr.dto.TerritoryRequestDTO;
import org.ezequiel.proyectofinal.features.hr.dto.TerritoryResponseDTO;
import org.ezequiel.proyectofinal.features.hr.entity.Territory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface TerritoryMapper {

    @Mapping(target = "region", ignore = true)
    @Mapping(target = "employeeTerritories", ignore = true)
    Territory toEntity(TerritoryRequestDTO dto);

    @Mapping(target = "regionId", source = "region.regionId")
    @Mapping(target = "regionDescription", source = "region.regionDescription")
    TerritoryResponseDTO toResponseDTO(Territory entity);

    @Mapping(target = "regionId", source = "region.regionId")
    TerritoryRequestDTO toRequestDTO(Territory entity);

    @Mapping(target = "region", ignore = true)
    @Mapping(target = "employeeTerritories", ignore = true)
    void updateEntityFromDTO(TerritoryRequestDTO dto, @MappingTarget Territory entity);
}
