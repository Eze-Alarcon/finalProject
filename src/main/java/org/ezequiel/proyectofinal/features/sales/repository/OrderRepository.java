package org.ezequiel.proyectofinal.features.sales.repository;

import org.ezequiel.proyectofinal.features.sales.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Short> {
}
