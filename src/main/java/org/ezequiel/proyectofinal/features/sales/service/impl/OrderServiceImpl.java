package org.ezequiel.proyectofinal.features.sales.service.impl;

import lombok.RequiredArgsConstructor;
import org.ezequiel.proyectofinal.core.exceptions.ResourceNotFoundException;
import org.ezequiel.proyectofinal.features.hr.entity.Employee;
import org.ezequiel.proyectofinal.features.hr.repository.EmployeeRepository;
import org.ezequiel.proyectofinal.features.sales.dto.OrderRequestDTO;
import org.ezequiel.proyectofinal.features.sales.dto.OrderResponseDTO;
import org.ezequiel.proyectofinal.features.sales.entity.Customer;
import org.ezequiel.proyectofinal.features.sales.entity.Order;
import org.ezequiel.proyectofinal.features.sales.entity.Shipper;
import org.ezequiel.proyectofinal.features.sales.mapper.OrderMapper;
import org.ezequiel.proyectofinal.features.sales.repository.CustomerRepository;
import org.ezequiel.proyectofinal.features.sales.repository.OrderRepository;
import org.ezequiel.proyectofinal.features.sales.repository.ShipperRepository;
import org.ezequiel.proyectofinal.features.sales.service.OrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final ShipperRepository shipperRepository;
    private final EmployeeRepository employeeRepository;
    private final OrderMapper orderMapper;

    @Override
    public List<OrderResponseDTO> findAll() {
        return orderRepository.findAll()
                .stream()
                .map(orderMapper::toResponseDTO)
                .toList();
    }

    @Override
    public OrderResponseDTO findById(Short id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order", id));
        return orderMapper.toResponseDTO(order);
    }

    @Override
    @Transactional
    public OrderResponseDTO save(OrderRequestDTO dto) {
        Order order = orderMapper.toEntity(dto);
        resolveRelations(order, dto);
        Order saved = orderRepository.save(order);
        return orderMapper.toResponseDTO(saved);
    }

    @Override
    @Transactional
    public OrderResponseDTO update(Short id, OrderRequestDTO dto) {
        Order existing = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order", id));
        orderMapper.updateEntityFromDTO(dto, existing);
        resolveRelations(existing, dto);
        Order updated = orderRepository.save(existing);
        return orderMapper.toResponseDTO(updated);
    }

    @Override
    @Transactional
    public void delete(Short id) {
        if (!orderRepository.existsById(id)) {
            throw new ResourceNotFoundException("Order", id);
        }
        orderRepository.deleteById(id);
    }

    private void resolveRelations(Order order, OrderRequestDTO dto) {
        if (dto.getCustomerId() != null) {
            Customer customer = customerRepository.findById(dto.getCustomerId())
                    .orElseThrow(() -> new ResourceNotFoundException("Customer", dto.getCustomerId()));
            order.setCustomer(customer);
        } else {
            order.setCustomer(null);
        }

        if (dto.getEmployeeId() != null) {
            Employee employee = employeeRepository.findById(dto.getEmployeeId())
                    .orElseThrow(() -> new ResourceNotFoundException("Employee", dto.getEmployeeId()));
            order.setEmployee(employee);
        } else {
            order.setEmployee(null);
        }

        if (dto.getShipperId() != null) {
            Shipper shipper = shipperRepository.findById(dto.getShipperId())
                    .orElseThrow(() -> new ResourceNotFoundException("Shipper", dto.getShipperId()));
            order.setShipper(shipper);
        } else {
            order.setShipper(null);
        }
    }
}
