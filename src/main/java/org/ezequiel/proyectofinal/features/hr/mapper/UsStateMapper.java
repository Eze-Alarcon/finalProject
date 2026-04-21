package org.ezequiel.proyectofinal.features.hr.mapper;

import org.ezequiel.proyectofinal.features.hr.dto.UsStateRequestDTO;
import org.ezequiel.proyectofinal.features.hr.dto.UsStateResponseDTO;
import org.ezequiel.proyectofinal.features.hr.entity.UsState;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UsStateMapper {

    UsState toEntity(UsStateRequestDTO dto);

    UsStateResponseDTO toResponseDTO(UsState entity);

    UsStateRequestDTO toRequestDTO(UsState entity);

    void updateEntityFromDTO(UsStateRequestDTO dto, @MappingTarget UsState entity);
}
