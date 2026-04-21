package org.ezequiel.proyectofinal.features.catalog.repository;

import org.ezequiel.proyectofinal.features.catalog.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Short> {
}
