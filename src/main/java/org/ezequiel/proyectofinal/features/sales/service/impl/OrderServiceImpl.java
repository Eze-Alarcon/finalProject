package org.ezequiel.proyectofinal.features.sales.service.impl;

import lombok.RequiredArgsConstructor;
import org.ezequiel.proyectofinal.core.exceptions.BadRequestException;
import org.ezequiel.proyectofinal.core.exceptions.ConflictException;
import org.ezequiel.proyectofinal.core.exceptions.ResourceNotFoundException;
import org.ezequiel.proyectofinal.features.catalog.entity.Product;
import org.ezequiel.proyectofinal.features.catalog.repository.ProductRepository;
import org.ezequiel.proyectofinal.features.hr.entity.Employee;
import org.ezequiel.proyectofinal.features.hr.repository.EmployeeRepository;
import org.ezequiel.proyectofinal.features.sales.dto.OrderDetailRequestDTO;
import org.ezequiel.proyectofinal.features.sales.dto.OrderRequestDTO;
import org.ezequiel.proyectofinal.features.sales.dto.OrderResponseDTO;
import org.ezequiel.proyectofinal.features.sales.entity.*;
import org.ezequiel.proyectofinal.features.sales.mapper.OrderDetailMapper;
import org.ezequiel.proyectofinal.features.sales.mapper.OrderMapper;
import org.ezequiel.proyectofinal.features.sales.repository.CustomerRepository;
import org.ezequiel.proyectofinal.features.sales.repository.OrderDetailRepository;
import org.ezequiel.proyectofinal.features.sales.repository.OrderRepository;
import org.ezequiel.proyectofinal.features.sales.repository.ShipperRepository;
import org.ezequiel.proyectofinal.features.sales.service.OrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final ShipperRepository shipperRepository;
    private final EmployeeRepository employeeRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final ProductRepository productRepository;
    private final OrderMapper orderMapper;
    private final OrderDetailMapper orderDetailMapper;

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
        order.setStatus(OrderStatus.PENDING);

        if (dto.getDetails() != null) {
            for (OrderDetailRequestDTO detailDTO : dto.getDetails()) {
                Product product = productRepository.findById(detailDTO.getProductId())
                        .orElseThrow(() -> new ResourceNotFoundException("Product", detailDTO.getProductId()));

                if (product.getDiscontinued() != 0) {
                    throw new RuntimeException("Product " + product.getProductName() + " is discontinued");
                }

                if (product.getUnitsInStock() < detailDTO.getQuantity()) {
                    throw new RuntimeException("Insufficient stock for product: " + product.getProductName());
                }

                product.setUnitsInStock((short) (product.getUnitsInStock() - detailDTO.getQuantity()));
                short onOrder = product.getUnitsOnOrder() != null ? product.getUnitsOnOrder() : 0;
                product.setUnitsOnOrder((short) (onOrder + detailDTO.getQuantity()));
                productRepository.save(product);

                OrderDetail orderDetail = orderDetailMapper.toEntity(detailDTO);
                orderDetail.setOrder(order);
                orderDetail.setProduct(product);
                orderDetail.setUnitPrice(product.getUnitPrice()); // Precio real de la DB
                orderDetail.setId(new OrderDetailId());

                order.getDetails().add(orderDetail);
            }
        }

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
    public OrderResponseDTO shipOrder(Short id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order", id));

        if (order.getStatus() != OrderStatus.PENDING) {
            throw new BadRequestException("Only PENDING orders can be shipped. Current status: " + order.getStatus());
        }

        if (order.getShipper() == null) {
            throw new BadRequestException("Order must have a shipper assigned before shipping.");
        }

        order.setStatus(OrderStatus.SHIPPED);
        order.setShippedDate(LocalDate.now());

        Order saved = orderRepository.save(order);
        return orderMapper.toResponseDTO(saved);
    }

    @Override
    @Transactional
    public OrderResponseDTO cancelOrder(Short id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order", id));

        if (order.getStatus() == OrderStatus.SHIPPED) {
            throw new ConflictException("No se puede cancelar un pedido que ya ha sido enviado");
        }

        if (order.getStatus() != OrderStatus.PENDING) {
            throw new BadRequestException("Only PENDING orders can be cancelled. Current status: " + order.getStatus());
        }

        if (order.getDetails() != null) {
            for (OrderDetail detail : order.getDetails()) {
                Product product = detail.getProduct();
                product.setUnitsInStock((short) (product.getUnitsInStock() + detail.getQuantity()));
                short onOrder = product.getUnitsOnOrder() != null ? product.getUnitsOnOrder() : 0;
                short newOnOrder = (short) (onOrder - detail.getQuantity());
                product.setUnitsOnOrder(newOnOrder < 0 ? (short) 0 : newOnOrder);
                productRepository.save(product);
            }
        }

        order.setStatus(OrderStatus.CANCELLED);

        Order saved = orderRepository.save(order);
        return orderMapper.toResponseDTO(saved);
    }

    @Override
    @Transactional
    public void delete(Short id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order", id));

        if (order.getDetails() != null) {
            for (OrderDetail detail : order.getDetails()) {
                Product product = detail.getProduct();
                product.setUnitsInStock((short) (product.getUnitsInStock() + detail.getQuantity()));
                productRepository.save(product);
            }
        }

        orderRepository.delete(order);
    }

    private void resolveRelations(Order order, OrderRequestDTO dto) {
        if (dto.getCustomerId() != null) {
            Customer customer = customerRepository.findById(dto.getCustomerId())
                    .orElseThrow(() -> new ResourceNotFoundException("Customer", dto.getCustomerId()));
            order.setCustomer(customer);
        }

        if (dto.getEmployeeId() != null) {
            Employee employee = employeeRepository.findById(dto.getEmployeeId())
                    .orElseThrow(() -> new ResourceNotFoundException("Employee", dto.getEmployeeId()));
            order.setEmployee(employee);
        }

        if (dto.getShipperId() != null) {
            Shipper shipper = shipperRepository.findById(dto.getShipperId())
                    .orElseThrow(() -> new ResourceNotFoundException("Shipper", dto.getShipperId()));
            order.setShipper(shipper);
        }
    }
}
