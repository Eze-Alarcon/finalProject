package org.ezequiel.proyectofinal.features.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "AuthResponseDTO", description = "Respuesta de autenticación con la información de acceso disponible en la API")
public class AuthResponseDTO {

    @Schema(
        description = "Token de acceso devuelto por el proceso de autenticación",
        example = "eyJhbGciOi...",
        nullable = true
    )
    private String token;

    @Schema(
        description = "Token de renovación devuelto por el proceso de autenticación",
        example = "def50200ab...",
        nullable = true
    )
    private String refreshToken;

    @Schema(
        description = "Nombre de usuario asociado a la autenticación realizada",
        example = "admin"
    )
    private String username;

    @Schema(
        description = "Rol asociado al usuario autenticado en la respuesta actual",
        example = "ADMIN",
        nullable = true
    )
    private String role;
}
