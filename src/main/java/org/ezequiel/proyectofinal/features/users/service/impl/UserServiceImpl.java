package org.ezequiel.proyectofinal.features.users.service.impl;

import lombok.RequiredArgsConstructor;
import org.ezequiel.proyectofinal.core.exceptions.BadRequestException;
import org.ezequiel.proyectofinal.core.exceptions.ResourceNotFoundException;
import org.ezequiel.proyectofinal.features.hr.entity.Employee;
import org.ezequiel.proyectofinal.features.hr.repository.EmployeeRepository;
import org.ezequiel.proyectofinal.features.users.dto.PasswordChangeRequestDTO;
import org.ezequiel.proyectofinal.features.users.dto.RoleChangeRequestDTO;
import org.ezequiel.proyectofinal.features.users.dto.UserRequestDTO;
import org.ezequiel.proyectofinal.features.users.dto.UserResponseDTO;
import org.ezequiel.proyectofinal.features.users.entity.User;
import org.ezequiel.proyectofinal.features.users.mapper.UserMapper;
import org.ezequiel.proyectofinal.features.users.repository.UserRepository;
import org.ezequiel.proyectofinal.features.users.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final EmployeeRepository employeeRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public UserResponseDTO create(UserRequestDTO dto) {
        if (userRepository.existsByUsername(dto.getUsername())) {
            throw new RuntimeException("Username already exists: " + dto.getUsername());
        }

        User user = userMapper.toEntity(dto);
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setEnabled(true);

        if (dto.getEmployeeId() != null) {
            Employee employee = employeeRepository.findById(dto.getEmployeeId())
                    .orElseThrow(() -> new ResourceNotFoundException("Employee", dto.getEmployeeId()));
            
            if (userRepository.existsByEmployee_EmployeeId(dto.getEmployeeId())) {
                throw new RuntimeException("Employee already has an assigned user");
            }
            user.setEmployee(employee);
        }

        User saved = userRepository.save(user);
        return userMapper.toResponseDTO(saved);
    }

    @Override
    @Transactional
    public UserResponseDTO updateStatus(Integer userId, Boolean enabled) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", userId));
        
        user.setEnabled(enabled);
        User saved = userRepository.save(user);
        return userMapper.toResponseDTO(saved);
    }

    @Override
    @Transactional
    public UserResponseDTO changePassword(Integer userId, PasswordChangeRequestDTO dto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", userId));
        
        if (dto.getOldPassword() != null && !dto.getOldPassword().isBlank()) {
            if (!passwordEncoder.matches(dto.getOldPassword(), user.getPassword())) {
                throw new BadRequestException("Current password does not match");
            }
        }
        
        user.setPassword(passwordEncoder.encode(dto.getNewPassword()));
        User saved = userRepository.save(user);
        return userMapper.toResponseDTO(saved);
    }

    @Override
    @Transactional
    public UserResponseDTO changeRole(Integer userId, RoleChangeRequestDTO dto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", userId));
        
        user.setRole(dto.getRole());
        User saved = userRepository.save(user);
        return userMapper.toResponseDTO(saved);
    }

    @Override
    public List<UserResponseDTO> findAll() {
        return userRepository.findAll().stream()
                .map(userMapper::toResponseDTO)
                .toList();
    }

    @Override
    public UserResponseDTO findById(Integer userId) {
        return userRepository.findById(userId)
                .map(userMapper::toResponseDTO)
                .orElseThrow(() -> new ResourceNotFoundException("User", userId));
    }
}
