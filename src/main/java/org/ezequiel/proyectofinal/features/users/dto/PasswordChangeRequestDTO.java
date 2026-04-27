package org.ezequiel.proyectofinal.features.users.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Datos requeridos para cambiar la contraseña de un usuario")
public class PasswordChangeRequestDTO {
    
    @Schema(
            description = "Contraseña actual del usuario (requerida para validar la operación). Los administradores pueden omitir este campo",
            example = "CurrentPass123",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    private String oldPassword;
    
    @Schema(
            description = "Nueva contraseña que reemplazará a la actual",
            example = "NewSecurePass456",
            minLength = 4,
            maxLength = 255,
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotBlank(message = "New password is required")
    @Size(min = 4, max = 255, message = "Password must be between 4 and 255 characters")
    private String newPassword;
}
