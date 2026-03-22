package org.ezequiel.proyectofinal.features.hr.repository;

import org.ezequiel.proyectofinal.features.hr.entity.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegionRepository extends JpaRepository<Region, Short> {
}
