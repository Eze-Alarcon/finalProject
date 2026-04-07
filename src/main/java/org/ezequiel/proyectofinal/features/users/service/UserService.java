package org.ezequiel.proyectofinal.features.users.service;

import org.ezequiel.proyectofinal.features.users.dto.PasswordChangeRequestDTO;
import org.ezequiel.proyectofinal.features.users.dto.RoleChangeRequestDTO;
import org.ezequiel.proyectofinal.features.users.dto.UserRequestDTO;
import org.ezequiel.proyectofinal.features.users.dto.UserResponseDTO;

import java.util.List;

public interface UserService {
    UserResponseDTO create(UserRequestDTO dto);
    UserResponseDTO updateStatus(Integer userId, Boolean enabled);
    UserResponseDTO changePassword(Integer userId, PasswordChangeRequestDTO dto);
    UserResponseDTO changeRole(Integer userId, RoleChangeRequestDTO dto);
    List<UserResponseDTO> findAll();
    UserResponseDTO findById(Integer userId);
}
