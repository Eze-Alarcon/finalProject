package org.ezequiel.proyectofinal.features.hr.repository;
  
import org.ezequiel.proyectofinal.features.hr.entity.Territory;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
  
import java.util.List;
import java.util.Optional;
  
@Repository
public interface TerritoryRepository extends JpaRepository<Territory, String> {
  
    @EntityGraph(attributePaths = {"region"})
    Optional<Territory> findById(String id);

    @EntityGraph(attributePaths = {"region"})
    List<Territory> findAll();
}
