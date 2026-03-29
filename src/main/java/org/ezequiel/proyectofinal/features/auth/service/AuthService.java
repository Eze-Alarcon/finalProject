package org.ezequiel.proyectofinal.features.auth.service;

import lombok.RequiredArgsConstructor;
import org.ezequiel.proyectofinal.core.security.JwtService;
import org.ezequiel.proyectofinal.features.auth.dto.AuthResponseDTO;
import org.ezequiel.proyectofinal.features.auth.dto.LoginRequestDTO;
import org.ezequiel.proyectofinal.features.auth.dto.RefreshTokenRequestDTO;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    // Beans creados en ApplicationConfig (Spring los inyecta automáticamente)
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtService jwtService;

    // Autentica al usuario y genera un par de tokens (access y refresh)
    public AuthResponseDTO login(LoginRequestDTO request) {
        /*
         * AuthenticationManager busca un componente llamado UserDetailsService
         * que esta configurado en ApplicationConfig
         *
         * El authManager llama a la db
         *
         * UsernamePasswordAuthenticationToken es un contenedor de credencial de un usuario aún no autenticado
         * */
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        // el Principal es "el usuario que actualmente estamos identificando"
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String accessToken = jwtService.generateToken(userDetails);
        String refreshToken = jwtService.generateRefreshToken(userDetails);

        // el userDetails.getAuthorities() devuelve una lista de roles del usuario
        // uso la stream api para obtener el primer rol del usuario
        String role = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .findFirst()
                .orElse(null);

        return new AuthResponseDTO(accessToken, refreshToken, userDetails.getUsername(), role);
    }

    // Válida el refresh token y emite un nuevo access token
    public AuthResponseDTO refreshToken(RefreshTokenRequestDTO request) {
        String refreshToken = request.getRefreshToken();
        String username = jwtService.extractUsername(refreshToken);

        if (username != null) {
            // El UserDetailsService es el que se encarga de consultar la db
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            // El jwtService es el que se encarga de validar el refresh token
            if (jwtService.isTokenValid(refreshToken, userDetails)) {
                String accessToken = jwtService.generateToken(userDetails);
                
                String role = userDetails.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .findFirst()
                        .orElse(null);

                return new AuthResponseDTO(accessToken, refreshToken, userDetails.getUsername(), role);
            }
        }
        throw new BadCredentialsException("Refresh token invalido o expirado");
    }
}
