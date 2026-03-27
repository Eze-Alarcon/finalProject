package org.ezequiel.proyectofinal.core.security;

/**
 * Spring Data JPA projection for authentication purposes.
 * Fetches only the fields strictly required to build a UserDetails object,
 * avoiding the loading of heavy columns such as photo (BYTEA) and notes (TEXT).
 */
public interface AuthUserProjection {

    Short getEmployeeId();

    String getUsername();

    String getPasswordHash();

    String getRole();

    Boolean getActivo();
}
