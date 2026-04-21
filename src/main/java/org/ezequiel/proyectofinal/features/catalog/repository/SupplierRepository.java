package org.ezequiel.proyectofinal.features.catalog.repository;

import org.ezequiel.proyectofinal.features.catalog.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Short> {
}
