package org.ezequiel.proyectofinal.features.sales.service;

import org.ezequiel.proyectofinal.features.sales.dto.OrderRequestDTO;
import org.ezequiel.proyectofinal.features.sales.dto.OrderResponseDTO;

import java.util.List;

public interface OrderService {

    List<OrderResponseDTO> findAll();

    OrderResponseDTO findById(Short id);

    OrderResponseDTO save(OrderRequestDTO dto);

    OrderResponseDTO update(Short id, OrderRequestDTO dto);

    void delete(Short id);
}
