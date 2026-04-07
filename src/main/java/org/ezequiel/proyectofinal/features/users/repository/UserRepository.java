package org.ezequiel.proyectofinal.features.users.repository;

import org.ezequiel.proyectofinal.features.users.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByEmployee_EmployeeId(Short employeeId);
}
