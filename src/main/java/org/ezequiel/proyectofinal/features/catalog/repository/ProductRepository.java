package org.ezequiel.proyectofinal.features.catalog.repository;

import org.ezequiel.proyectofinal.features.catalog.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Short> {
}
