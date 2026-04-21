package org.ezequiel.proyectofinal.features.users.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.ezequiel.proyectofinal.features.users.dto.PasswordChangeRequestDTO;
import org.ezequiel.proyectofinal.features.users.dto.RoleChangeRequestDTO;
import org.ezequiel.proyectofinal.features.users.dto.UserRequestDTO;
import org.ezequiel.proyectofinal.features.users.dto.UserResponseDTO;
import org.ezequiel.proyectofinal.features.users.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserResponseDTO> create(@Valid @RequestBody UserRequestDTO dto) {
        UserResponseDTO created = userService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PatchMapping("/{userId}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserResponseDTO> updateStatus(
            @PathVariable Integer userId,
            @RequestBody Boolean enabled) {
        return ResponseEntity.ok(userService.updateStatus(userId, enabled));
    }

    @PatchMapping("/{userId}/password")
    @PreAuthorize("hasRole('ADMIN') or #userId == principal.userId")
    public ResponseEntity<UserResponseDTO> changePassword(
            @PathVariable Integer userId,
            @Valid @RequestBody PasswordChangeRequestDTO dto) {
        return ResponseEntity.ok(userService.changePassword(userId, dto));
    }

    @PatchMapping("/{userId}/role")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserResponseDTO> changeRole(
            @PathVariable Integer userId,
            @Valid @RequestBody RoleChangeRequestDTO dto) {
        return ResponseEntity.ok(userService.changeRole(userId, dto));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserResponseDTO>> findAll() {
        return ResponseEntity.ok(userService.findAll());
    }

    @GetMapping("/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserResponseDTO> findById(@PathVariable Integer userId) {
        return ResponseEntity.ok(userService.findById(userId));
    }
}
