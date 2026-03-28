package org.ezequiel.proyectofinal.features.catalog.repository;

import org.ezequiel.proyectofinal.features.catalog.entity.Product;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Short> {

    @EntityGraph(attributePaths = {"category", "supplier"})
    List<Product> findAll();
}
