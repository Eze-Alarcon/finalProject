package org.ezequiel.proyectofinal.features.users.mapper;

import org.ezequiel.proyectofinal.features.users.dto.UserRequestDTO;
import org.ezequiel.proyectofinal.features.users.dto.UserResponseDTO;
import org.ezequiel.proyectofinal.features.users.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "enabled", ignore = true)
    @Mapping(target = "employee", ignore = true)
    User toEntity(UserRequestDTO dto);

    @Mapping(target = "employeeId", source = "employee.employeeId")
    UserResponseDTO toResponseDTO(User entity);
}
