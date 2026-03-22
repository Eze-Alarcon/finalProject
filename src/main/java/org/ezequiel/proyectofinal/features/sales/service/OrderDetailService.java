package org.ezequiel.proyectofinal.features.sales.service;

import org.ezequiel.proyectofinal.features.sales.dto.OrderDetailRequestDTO;
import org.ezequiel.proyectofinal.features.sales.dto.OrderDetailResponseDTO;

import java.util.List;

public interface OrderDetailService {

    List<OrderDetailResponseDTO> findAll();

    OrderDetailResponseDTO findById(Short orderId, Short productId);

    OrderDetailResponseDTO save(OrderDetailRequestDTO dto);

    OrderDetailResponseDTO update(Short orderId, Short productId, OrderDetailRequestDTO dto);

    void delete(Short orderId, Short productId);
}
