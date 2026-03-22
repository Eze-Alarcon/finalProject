package org.ezequiel.proyectofinal.features.hr.repository;

import org.ezequiel.proyectofinal.features.hr.entity.EmployeeTerritory;
import org.ezequiel.proyectofinal.features.hr.entity.EmployeeTerritoryId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeTerritoryRepository extends JpaRepository<EmployeeTerritory, EmployeeTerritoryId> {
}
