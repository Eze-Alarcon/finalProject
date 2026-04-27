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
@Schema(name = "RefreshTokenRequestDTO", description = "Datos necesarios para solicitar una renovación de acceso")
public class RefreshTokenRequestDTO {

    @NotBlank(message = "El refresh token es obligatorio")
    @Schema(
            description = "Token de renovación enviado por el cliente para solicitar una nueva respuesta de acceso. Es un valor sensible y no debe exponerse en logs o interfaces públicas.",
            example = "def50200ab...",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String refreshToken;
}
