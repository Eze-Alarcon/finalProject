package org.ezequiel.proyectofinal.features.sales.repository;

import org.ezequiel.proyectofinal.features.sales.dto.CustomerOrderHistoryDTO;
import org.ezequiel.proyectofinal.features.sales.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Short> {

    @Query("SELECT DISTINCT o FROM Order o " +
           "LEFT JOIN FETCH o.customer " +
           "LEFT JOIN FETCH o.employee " +
           "LEFT JOIN FETCH o.shipper " +
           "LEFT JOIN FETCH o.details d " +
           "LEFT JOIN FETCH d.product")
    List<Order> findAll();

    @Query("SELECT DISTINCT o FROM Order o " +
           "LEFT JOIN FETCH o.customer " +
           "LEFT JOIN FETCH o.employee " +
           "LEFT JOIN FETCH o.shipper " +
           "LEFT JOIN FETCH o.details d " +
           "LEFT JOIN FETCH d.product " +
           "WHERE o.orderId = :id")
    Optional<Order> findById(@Param("id") Short id);

    @Query("SELECT new org.ezequiel.proyectofinal.features.sales.dto.CustomerOrderHistoryDTO(" +
           "o.orderId, o.orderDate, o.status, CAST(SUM(d.unitPrice * d.quantity * (1 - d.discount)) as double)) " +
           "FROM Order o " +
           "JOIN o.details d " +
           "WHERE o.customer.customerId = :customerId " +
           "GROUP BY o.orderId, o.orderDate, o.status")
    Page<CustomerOrderHistoryDTO> findOrderHistoryByCustomerId(@Param("customerId") String customerId, Pageable pageable);
}
