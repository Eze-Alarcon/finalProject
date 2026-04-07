package org.ezequiel.proyectofinal.features.sales.service.impl;

import lombok.RequiredArgsConstructor;
import org.ezequiel.proyectofinal.core.exceptions.ResourceNotFoundException;
import org.ezequiel.proyectofinal.features.catalog.entity.Product;
import org.ezequiel.proyectofinal.features.catalog.repository.ProductRepository;
import org.ezequiel.proyectofinal.features.sales.dto.OrderDetailRequestDTO;
import org.ezequiel.proyectofinal.features.sales.dto.OrderDetailResponseDTO;
import org.ezequiel.proyectofinal.features.sales.entity.Order;
import org.ezequiel.proyectofinal.features.sales.entity.OrderDetail;
import org.ezequiel.proyectofinal.features.sales.entity.OrderDetailId;
import org.ezequiel.proyectofinal.features.sales.mapper.OrderDetailMapper;
import org.ezequiel.proyectofinal.features.sales.repository.OrderDetailRepository;
import org.ezequiel.proyectofinal.features.sales.repository.OrderRepository;
import org.ezequiel.proyectofinal.features.sales.service.OrderDetailService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderDetailServiceImpl implements OrderDetailService {

    private final OrderDetailRepository orderDetailRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final OrderDetailMapper orderDetailMapper;

    @Override
    public List<OrderDetailResponseDTO> findAll() {
        return orderDetailRepository.findAll()
                .stream()
                .map(orderDetailMapper::toResponseDTO)
                .toList();
    }

    @Override
    public OrderDetailResponseDTO findById(Short orderId, Short productId) {
        OrderDetailId id = new OrderDetailId(orderId, productId);
        OrderDetail orderDetail = orderDetailRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("OrderDetail with orderId '%s' and productId '%s' not found",
                                orderId, productId)));
        return orderDetailMapper.toResponseDTO(orderDetail);
    }

    @Override
    @Transactional
    public void delete(Short orderId, Short productId) {
        OrderDetailId id = new OrderDetailId(orderId, productId);
        if (!orderDetailRepository.existsById(id)) {
            throw new ResourceNotFoundException(
                    String.format("OrderDetail with orderId '%s' and productId '%s' not found",
                            orderId, productId));
        }
        orderDetailRepository.deleteById(id);
    }
}
