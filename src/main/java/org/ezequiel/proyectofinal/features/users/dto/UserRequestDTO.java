package org.ezequiel.proyectofinal.features.users.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Datos requeridos para crear un nuevo usuario en el sistema")
public class UserRequestDTO {

    @Schema(
            description = "Nombre de usuario único para el acceso al sistema",
            example = "jsmith",
            maxLength = 50,
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotBlank(message = "Username is required")
    @Size(max = 50, message = "Username must not exceed 50 characters")
    private String username;

    @Schema(
            description = "Contraseña del usuario (debe tener al menos 4 caracteres)",
            example = "SecurePass123",
            minLength = 4,
            maxLength = 255,
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotBlank(message = "Password is required")
    @Size(min = 4, max = 255, message = "Password must be between 4 and 255 characters")
    private String password;

    @Schema(
            description = "Rol asignado al usuario que determina sus permisos en el sistema",
            example = "USER",
            allowableValues = {"ADMIN", "USER", "MANAGER"},
            maxLength = 50,
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotBlank(message = "Role is required")
    @Size(max = 50, message = "Role must not exceed 50 characters")
    private String role;

    @Schema(
            description = "ID del empleado asociado (opcional). Permite vincular el usuario con un registro de empleado existente",
            example = "123",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    private Short employeeId;
}
