package org.ezequiel.proyectofinal.features.auth.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.ezequiel.proyectofinal.features.auth.dto.AuthResponseDTO;
import org.ezequiel.proyectofinal.features.auth.dto.LoginRequestDTO;
import org.ezequiel.proyectofinal.features.auth.dto.RefreshTokenRequestDTO;
import org.ezequiel.proyectofinal.features.auth.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Auth", description = "API de autenticación del sistema. Expone el inicio de sesión y la renovación de credenciales de acceso, sin documentar mecanismos internos de sesión o persistencia.")
public class AuthController {

    private final AuthService authService;

        @Operation(
            summary = "Iniciar sesión",
            description = "Autentica a un usuario con sus credenciales y devuelve la información de acceso disponible en la respuesta. Este endpoint está pensado para clientes frontend que necesitan iniciar sesión en la aplicación.",
            security = {}
        )
        @ApiResponses({
            @ApiResponse(
                responseCode = "200",
                description = "Autenticación exitosa",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = AuthResponseDTO.class),
                    examples = @ExampleObject(
                        name = "LoginSuccessExample",
                        summary = "Respuesta de autenticación exitosa",
                        value = """
                            {
                              "token": "eyJhbGciOi...",
                              "refreshToken": "def50200ab...",
                              "username": "admin",
                              "role": "ADMIN"
                            }
                            """
                    )
                )
            ),
            @ApiResponse(
                responseCode = "400",
                description = "Datos de entrada inválidos",
                content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                responseCode = "401",
                description = "Credenciales inválidas o usuario no autorizado",
                content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                responseCode = "500",
                description = "Error interno del servidor",
                content = @Content(mediaType = "application/json")
            )
        })
    @PostMapping("/login")
        public ResponseEntity<AuthResponseDTO> login(
            @RequestBody(
                description = "Credenciales del usuario para iniciar sesión",
                required = true,
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = LoginRequestDTO.class),
                    examples = @ExampleObject(
                        name = "LoginRequestExample",
                        summary = "Credenciales de ejemplo",
                        value = """
                            {
                              "username": "admin",
                              "password": "Admin123!"
                            }
                            """
                    )
                )
            )
            @Valid @org.springframework.web.bind.annotation.RequestBody LoginRequestDTO request) {
        return ResponseEntity.ok(authService.login(request));
    }

        @Operation(
            summary = "Renovar acceso",
            description = "Renueva la información de acceso a partir de los datos enviados en la solicitud. El contrato del endpoint solo expone el payload de entrada y la respuesta disponible en el código, sin asumir detalles de implementación interna.",
            security = {}
        )
        @ApiResponses({
            @ApiResponse(
                responseCode = "200",
                description = "Renovación exitosa",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = AuthResponseDTO.class),
                    examples = @ExampleObject(
                        name = "RefreshSuccessExample",
                        summary = "Respuesta de renovación exitosa",
                        value = """
                            {
                              "token": "eyJhbGciOi...",
                              "refreshToken": "def50200cd...",
                              "username": "admin",
                              "role": "ADMIN"
                            }
                            """
                    )
                )
            ),
            @ApiResponse(
                responseCode = "400",
                description = "Datos de entrada inválidos",
                content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                responseCode = "401",
                description = "Refresh token inválido, expirado o no autorizado",
                content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                responseCode = "500",
                description = "Error interno del servidor",
                content = @Content(mediaType = "application/json")
            )
        })
    @PostMapping("/refresh")
        public ResponseEntity<AuthResponseDTO> refresh(
            @RequestBody(
                description = "Datos necesarios para renovar el acceso",
                required = true,
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = RefreshTokenRequestDTO.class),
                    examples = @ExampleObject(
                        name = "RefreshRequestExample",
                        summary = "Solicitud de renovación de ejemplo",
                        value = """
                            {
                              "refreshToken": "def50200ab..."
                            }
                            """
                    )
                )
            )
            @Valid @org.springframework.web.bind.annotation.RequestBody RefreshTokenRequestDTO request) {
        return ResponseEntity.ok(authService.refreshToken(request));
    }
}
