package org.ezequiel.proyectofinal.core.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final AccountStatusUserDetailsChecker detailsChecker = new AccountStatusUserDetailsChecker();

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");

        // Este filtro es para peticiones que no requieren autenticación
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        final String jwt = authHeader.replace("Bearer ", "");
        final String username;

        try {
            username = jwtService.extractUsername(jwt);
        } catch (Exception e) {
            filterChain.doFilter(request, response);
            return;
        }

        /* El filtro SecurityContextHolder.getContext().getAuthentication() == null
         * puede ser redundante, pero una petición puede ser redirigida internamente por el servidor
         * (por ejemplo, a un controlador de errores).
         * Si el usuario ya fue autenticado en la primera parte de la petición,
         * no queremos que el filtro vuelva a validar el token y a llamar a la base de datos innecesariamente.
        */
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // esto esta en el ApplicationConfig.java
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            // Validamos que la cuenta esté habilitada, no expirada, etc.
            // Si no es válida, lanzará una excepción que cortará el flujo de autenticación para esta petición.
            try {
                detailsChecker.check(userDetails);

                if (jwtService.isTokenValid(jwt, userDetails)) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );
                    // con esta línea puedo obtener mas datos de la conexión como ip, etc
                    // de momento se guarda por lo que dure la llamada HTTP, esta info no se periste
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    // guardamos la autenticación en el contexto de seguridad
                    // si el servidor hace una redireccion la peticion no entrara en el bucle
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            } catch (Exception e) {
                // Si la cuenta está deshabilitada o bloqueada, no establecemos la autenticación
                // y dejamos que continúe la cadena de filtros para que Spring Security maneje el acceso denegado.
            }
        }

        // continuamos con el siguiente filtro en la cadena que sera el controller
        filterChain.doFilter(request, response);
    }
}
