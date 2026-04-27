package org.ezequiel.proyectofinal.features.users.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.ezequiel.proyectofinal.features.users.dto.*;
import org.ezequiel.proyectofinal.features.users.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Tag(name = "Users", description = "API de gestión de usuarios del sistema. Permite crear, consultar y administrar usuarios con diferentes roles y permisos.")
public class UserController {

    private final UserService userService;

    @Operation(
            summary = "Crear nuevo usuario",
            description = "Crea un nuevo usuario en el sistema. Solo los administradores pueden crear usuarios. " +
                    "El usuario se creará con los datos proporcionados y se asignará el rol especificado.",
            security = @SecurityRequirement(name = "bearer")
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201", 
                    description = "Usuario creado exitosamente",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UserResponseDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400", 
                    description = "Datos de entrada inválidos o usuario ya existe",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "401", 
                    description = "Token de autenticación no válido o ausente",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "403", 
                    description = "Sin permisos suficientes - Se requiere rol ADMIN",
                    content = @Content(mediaType = "application/json")
            )
    })
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserResponseDTO> create(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos del nuevo usuario a crear",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UserRequestDTO.class)
                    )
            )
            @Valid @RequestBody UserRequestDTO dto) {
        UserResponseDTO created = userService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @Operation(
            summary = "Actualizar estado del usuario",
            description = "Permite habilitar o deshabilitar un usuario específico. Solo los administradores " +
                    "pueden modificar el estado de los usuarios. Un usuario deshabilitado no podrá acceder al sistema.",
            security = @SecurityRequirement(name = "bearer")
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200", 
                    description = "Estado del usuario actualizado exitosamente",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UserResponseDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400", 
                    description = "Datos de entrada inválidos",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "401", 
                    description = "Token de autenticación no válido o ausente",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "403", 
                    description = "Sin permisos suficientes - Se requiere rol ADMIN",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "404", 
                    description = "Usuario no encontrado",
                    content = @Content(mediaType = "application/json")
            )
    })
    @PatchMapping("/{userId}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserResponseDTO> updateStatus(
            @Parameter(
                    description = "ID único del usuario a modificar", 
                    required = true,
                    example = "1"
            )
            @PathVariable Integer userId,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Nuevo estado del usuario (habilitado/deshabilitado)",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UserStatusUpdateDTO.class)
                    )
            )
            @RequestBody UserStatusUpdateDTO dto) {
        return ResponseEntity.ok(userService.updateStatus(userId, dto.getEnabled()));
    }

    @Operation(
            summary = "Cambiar contraseña de usuario",
            description = "Permite cambiar la contraseña de un usuario. Los administradores pueden cambiar la contraseña " +
                    "de cualquier usuario, mientras que los usuarios regulares solo pueden cambiar su propia contraseña. " +
                    "Se requiere la contraseña anterior para validar la operación.",
            security = @SecurityRequirement(name = "bearer")
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200", 
                    description = "Contraseña actualizada exitosamente",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UserResponseDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400", 
                    description = "Datos de entrada inválidos o contraseña actual incorrecta",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "401", 
                    description = "Token de autenticación no válido o ausente",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "403", 
                    description = "Sin permisos suficientes - Solo ADMIN o el propio usuario puede cambiar la contraseña",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "404", 
                    description = "Usuario no encontrado",
                    content = @Content(mediaType = "application/json")
            )
    })
    @PatchMapping("/{userId}/password")
    @PreAuthorize("hasRole('ADMIN') or #userId == principal.userId")
    public ResponseEntity<UserResponseDTO> changePassword(
            @Parameter(
                    description = "ID único del usuario cuya contraseña se desea cambiar", 
                    required = true,
                    example = "1"
            )
            @PathVariable Integer userId,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos para el cambio de contraseña incluyendo contraseña actual y nueva",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = PasswordChangeRequestDTO.class)
                    )
            )
            @Valid @RequestBody PasswordChangeRequestDTO dto) {
        return ResponseEntity.ok(userService.changePassword(userId, dto));
    }

    @Operation(
            summary = "Cambiar rol de usuario",
            description = "Modifica el rol asignado a un usuario específico. Solo los administradores pueden " +
                    "realizar cambios de roles. Los roles disponibles determinan los permisos del usuario en el sistema.",
            security = @SecurityRequirement(name = "bearer")
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200", 
                    description = "Rol del usuario actualizado exitosamente",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UserResponseDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400", 
                    description = "Datos de entrada inválidos o rol no válido",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "401", 
                    description = "Token de autenticación no válido o ausente",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "403", 
                    description = "Sin permisos suficientes - Se requiere rol ADMIN",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "404", 
                    description = "Usuario no encontrado",
                    content = @Content(mediaType = "application/json")
            )
    })
    @PatchMapping("/{userId}/role")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserResponseDTO> changeRole(
            @Parameter(
                    description = "ID único del usuario cuyo rol se desea cambiar", 
                    required = true,
                    example = "1"
            )
            @PathVariable Integer userId,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Nuevo rol a asignar al usuario",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = RoleChangeRequestDTO.class)
                    )
            )
            @Valid @RequestBody RoleChangeRequestDTO dto) {
        return ResponseEntity.ok(userService.changeRole(userId, dto));
    }

    @Operation(
            summary = "Obtener todos los usuarios",
            description = "Recupera la lista completa de usuarios registrados en el sistema. Solo los administradores " +
                    "tienen acceso a esta información. Incluye datos básicos de cada usuario como ID, nombre, rol y estado.",
            security = @SecurityRequirement(name = "bearer")
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200", 
                    description = "Lista de usuarios recuperada exitosamente",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UserResponseDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "401", 
                    description = "Token de autenticación no válido o ausente",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "403", 
                    description = "Sin permisos suficientes - Se requiere rol ADMIN",
                    content = @Content(mediaType = "application/json")
            )
    })
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserResponseDTO>> findAll() {
        return ResponseEntity.ok(userService.findAll());
    }

    @Operation(
            summary = "Obtener usuario por ID",
            description = "Recupera la información detallada de un usuario específico mediante su ID único. " +
                    "Solo los administradores pueden acceder a esta información.",
            security = @SecurityRequirement(name = "bearer")
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200", 
                    description = "Usuario encontrado y datos recuperados exitosamente",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UserResponseDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "401", 
                    description = "Token de autenticación no válido o ausente",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "403", 
                    description = "Sin permisos suficientes - Se requiere rol ADMIN",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "404", 
                    description = "Usuario no encontrado",
                    content = @Content(mediaType = "application/json")
            )
    })
    @GetMapping("/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserResponseDTO> findById(
            @Parameter(
                    description = "ID único del usuario a consultar", 
                    required = true,
                    example = "1"
            )
            @PathVariable Integer userId) {
        return ResponseEntity.ok(userService.findById(userId));
    }
}
