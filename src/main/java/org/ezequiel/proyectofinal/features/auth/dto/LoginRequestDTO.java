package org.ezequiel.proyectofinal.features.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "LoginRequestDTO", description = "Datos necesarios para autenticar un usuario en la API")
public class LoginRequestDTO {

    @NotBlank(message = "Username is required")
    @Schema(
            description = "Nombre de usuario utilizado para iniciar sesión",
            example = "admin",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String username;

    @NotBlank(message = "Password is required")
    @Schema(
            description = "Contraseña del usuario. Es un dato sensible y debe enviarse únicamente por canales seguros",
            example = "Admin123!",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String password;
}
