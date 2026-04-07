package org.ezequiel.proyectofinal.features.hr.repository;

import org.ezequiel.proyectofinal.features.hr.entity.EmployeeTerritory;
import org.ezequiel.proyectofinal.features.hr.entity.EmployeeTerritoryId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeTerritoryRepository extends JpaRepository<EmployeeTerritory, EmployeeTerritoryId> {

    @Query(value = """
            SELECT et.employee_id as employeeId,
                concat(e.first_name, ' ', e.last_name) as employeeFullName,
                string_agg(t.territory_description, ', ') as territories
            FROM employee_territories et
            JOIN employees e ON et.employee_id = e.employee_id
            JOIN territories t ON et.territory_id = t.territory_id
            GROUP BY et.employee_id, e.first_name, e.last_name
            """, nativeQuery = true)
    List<EmployeeTerritoryProjection> findAllProjected();

    @Query(value = """
            SELECT et.employee_id as employeeId,
                concat(e.first_name, ' ', e.last_name) as employeeFullName,
                string_agg(t.territory_description, ', ') as territories
            FROM employee_territories et
            JOIN employees e ON et.employee_id = e.employee_id
            JOIN territories t ON et.territory_id = t.territory_id
            WHERE et.employee_id = :employeeId
            GROUP BY et.employee_id, e.first_name, e.last_name
            """, nativeQuery = true)
    Optional<EmployeeTerritoryProjection> findByEmployeeIdProjected(
            @Param("employeeId") Short employeeId);
}
