package org.ezequiel.proyectofinal.features.hr.repository;

import org.ezequiel.proyectofinal.features.hr.dto.EmployeeTerritoryResponseDTO;
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

    @Query("""
            SELECT new org.ezequiel.proyectofinal.features.hr.dto.EmployeeTerritoryResponseDTO(
                et.id.employeeId,
                concat(e.firstName, ' ', e.lastName),
                et.id.territoryId,
                t.territoryDescription
            )
            FROM EmployeeTerritory et
            JOIN et.employee e
            JOIN et.territory t
            """)
    List<EmployeeTerritoryResponseDTO> findAllProjected();

    @Query("""
            SELECT new org.ezequiel.proyectofinal.features.hr.dto.EmployeeTerritoryResponseDTO(
                et.id.employeeId,
                concat(e.firstName, ' ', e.lastName),
                et.id.territoryId,
                t.territoryDescription
            )
            FROM EmployeeTerritory et
            JOIN et.employee e
            JOIN et.territory t
            WHERE et.id.employeeId = :employeeId AND et.id.territoryId = :territoryId
            """)
    Optional<EmployeeTerritoryResponseDTO> findByIdProjected(
            @Param("employeeId") Short employeeId,
            @Param("territoryId") String territoryId);
}
