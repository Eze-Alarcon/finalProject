package org.ezequiel.proyectofinal.features.auth.service;

import lombok.RequiredArgsConstructor;
import org.ezequiel.proyectofinal.core.security.JwtService;
import org.ezequiel.proyectofinal.features.auth.dto.AuthResponseDTO;
import org.ezequiel.proyectofinal.features.auth.dto.LoginRequestDTO;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthResponseDTO login(LoginRequestDTO request) {
        /*
         * AuthenticationManager busca un componente llamado UserDetailsService
         * que esta configurado en ApplicationConfig
         *
         * El authManager llama a la db
         * */
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        // el Principal es simplemente "el usuario actualmente identificado"
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String token = jwtService.generateToken(userDetails);

        // el userDetails.getAuthorities() devuelve una lista de roles del usuario
        // uso la stream api para obtener el primer rol del usuario
        String role = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .findFirst()
                .orElse(null);

        return new AuthResponseDTO(token, userDetails.getUsername(), role);
    }
}
