package org.ezequiel.proyectofinal.features.sales.repository;

import org.ezequiel.proyectofinal.features.sales.entity.OrderDetail;
import org.ezequiel.proyectofinal.features.sales.entity.OrderDetailId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, OrderDetailId> {

    @Query("SELECT od FROM OrderDetail od " +
           "JOIN FETCH od.order " +
           "JOIN FETCH od.product")
    List<OrderDetail> findAll();

    @Query("SELECT od FROM OrderDetail od " +
           "JOIN FETCH od.order " +
           "JOIN FETCH od.product " +
           "WHERE od.id = :id")
    Optional<OrderDetail> findById(@Param("id") OrderDetailId id);
}
