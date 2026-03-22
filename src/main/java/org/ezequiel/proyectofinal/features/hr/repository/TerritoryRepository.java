package org.ezequiel.proyectofinal.features.hr.repository;

import org.ezequiel.proyectofinal.features.hr.entity.Territory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TerritoryRepository extends JpaRepository<Territory, String> {
}
