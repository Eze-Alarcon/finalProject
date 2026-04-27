package org.ezequiel.proyectofinal.features.users.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Datos requeridos para cambiar el rol de un usuario")
public class RoleChangeRequestDTO {
    
    @Schema(
            description = "Nuevo rol a asignar al usuario. Determina los permisos que tendrá en el sistema",
            example = "MANAGER",
            allowableValues = {"ADMIN", "USER", "MANAGER"},
            maxLength = 50,
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotBlank(message = "Role is required")
    @Size(max = 50, message = "Role must not exceed 50 characters")
    private String role;
}
