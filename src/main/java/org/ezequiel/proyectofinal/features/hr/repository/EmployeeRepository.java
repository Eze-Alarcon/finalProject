package org.ezequiel.proyectofinal.features.hr.repository;

import org.ezequiel.proyectofinal.core.security.AuthUserProjection;
import org.ezequiel.proyectofinal.features.hr.dto.EmployeeResponseDTO;
import org.ezequiel.proyectofinal.features.hr.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Short> {

    @Query("""
            SELECT new org.ezequiel.proyectofinal.features.hr.dto.EmployeeResponseDTO(
                e.employeeId, e.lastName, e.firstName, e.title, e.titleOfCourtesy,
                e.birthDate, e.hireDate, e.address, e.city, e.region, e.postalCode, e.country,
                e.homePhone, e.extension, e.notes,
                rt.employeeId, concat(rt.firstName, ' ', rt.lastName),
                e.photoPath, e.username, e.role, e.activo
            )
            FROM Employee e
            LEFT JOIN e.reportsTo rt
            """)
    List<EmployeeResponseDTO> findAllProjected();

    @Query("""
            SELECT new org.ezequiel.proyectofinal.features.hr.dto.EmployeeResponseDTO(
                e.employeeId, e.lastName, e.firstName, e.title, e.titleOfCourtesy,
                e.birthDate, e.hireDate, e.address, e.city, e.region, e.postalCode, e.country,
                e.homePhone, e.extension, e.notes,
                rt.employeeId, concat(rt.firstName, ' ', rt.lastName),
                e.photoPath, e.username, e.role, e.activo
            )
            FROM Employee e
            LEFT JOIN e.reportsTo rt
            WHERE e.employeeId = :id
            """)
    Optional<EmployeeResponseDTO> findByIdProjected(@Param("id") Short id);

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
