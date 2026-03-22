package org.ezequiel.proyectofinal.features.sales.repository;

import org.ezequiel.proyectofinal.features.sales.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, String> {
}
