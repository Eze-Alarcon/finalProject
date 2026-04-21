package org.ezequiel.proyectofinal.features.sales.repository;

import org.ezequiel.proyectofinal.features.sales.entity.Shipper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShipperRepository extends JpaRepository<Shipper, Short> {
}
