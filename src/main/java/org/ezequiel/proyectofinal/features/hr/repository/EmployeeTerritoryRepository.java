package org.ezequiel.proyectofinal.features.hr.repository;

import org.ezequiel.proyectofinal.features.hr.entity.EmployeeTerritory;
import org.ezequiel.proyectofinal.features.hr.entity.EmployeeTerritoryId;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeTerritoryRepository extends JpaRepository<EmployeeTerritory, EmployeeTerritoryId> {

    @EntityGraph(attributePaths = {"employee", "territory"})
    List<EmployeeTerritory> findAll();

    @EntityGraph(attributePaths = {"employee", "territory"})
    Optional<EmployeeTerritory> findById(EmployeeTerritoryId id);
}
