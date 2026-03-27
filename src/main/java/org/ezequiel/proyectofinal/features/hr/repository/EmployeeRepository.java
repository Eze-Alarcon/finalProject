package org.ezequiel.proyectofinal.features.hr.repository;

import org.ezequiel.proyectofinal.core.security.AuthUserProjection;
import org.ezequiel.proyectofinal.features.hr.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Short> {

    Optional<Employee> findByUsername(String username);

    /**
     * Optimized query for authentication: fetches only the fields required to
     * build a UserDetails object, skipping heavy columns (photo, notes, etc.).
     */
    @Query("""
            SELECT e.employeeId  AS employeeId,
                   e.username    AS username,
                   e.passwordHash AS passwordHash,
                   e.role        AS role,
                   e.activo      AS activo
            FROM Employee e
            WHERE e.username = :username
            """)
    Optional<AuthUserProjection> findAuthUserByUsername(@Param("username") String username);
}
