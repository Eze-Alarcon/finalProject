package org.ezequiel.proyectofinal.features.sales.service.impl;

import org.ezequiel.proyectofinal.core.exceptions.BadRequestException;
import org.ezequiel.proyectofinal.core.exceptions.ConflictException;
import org.ezequiel.proyectofinal.core.exceptions.InsufficientStockException;
import org.ezequiel.proyectofinal.core.exceptions.ResourceNotFoundException;
import org.ezequiel.proyectofinal.features.catalog.entity.Product;
import org.ezequiel.proyectofinal.features.catalog.repository.ProductRepository;
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
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    @Mock
    private OrderRepository orderRepository;
    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private ShipperRepository shipperRepository;
    @Mock
    private EmployeeRepository employeeRepository;
    @Mock
    private OrderDetailRepository orderDetailRepository;
    @Mock
    private ProductRepository productRepository;
    @Mock
    private OrderMapper orderMapper;
    @Mock
    private OrderDetailMapper orderDetailMapper;

    @InjectMocks
    private OrderServiceImpl orderService;

    // --- save (Crear orden) ---

    @Test
    void save_ShouldCreateOrderAndReduceStock() {
        // Arrange (Given)
        OrderRequestDTO requestDTO = new OrderRequestDTO();
        requestDTO.setDetails(List.of(new OrderDetailRequestDTO((short) 1, 10.0f, (short) 5, 0.0f)));
        
        Order orderEntity = new Order();
        orderEntity.setDetails(new ArrayList<>());
        
        Product product = new Product();
        product.setProductId((short) 1);
        product.setProductName("Test Product");
        product.setDiscontinued(0);
        product.setUnitsInStock((short) 10);
        product.setUnitsOnOrder((short) 0);
        product.setUnitPrice(10.0f);

        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setQuantity((short) 5);

        when(orderMapper.toEntity(requestDTO)).thenReturn(orderEntity);
        when(productRepository.findById((short) 1)).thenReturn(Optional.of(product));
        when(orderDetailMapper.toEntity(any(OrderDetailRequestDTO.class))).thenReturn(orderDetail);
        when(orderRepository.save(any(Order.class))).thenReturn(orderEntity);
        when(orderMapper.toResponseDTO(any(Order.class))).thenReturn(new OrderResponseDTO());

        // Act (When)
        orderService.save(requestDTO);

        // Assert (Then)
        assertEquals(OrderStatus.PENDING, orderEntity.getStatus());
        assertNull(orderEntity.getShippedDate());
        assertEquals((short) 5, product.getUnitsInStock());
        assertEquals((short) 5, product.getUnitsOnOrder());
        verify(productRepository).save(product);
        verify(orderRepository).save(orderEntity);
    }

    @Test
    void save_WhenProductDiscontinued_ShouldThrowBadRequestException() {
        // Arrange (Given)
        OrderRequestDTO requestDTO = new OrderRequestDTO();
        requestDTO.setDetails(List.of(new OrderDetailRequestDTO((short) 1, 10.0f, (short) 5, 0.0f)));
        
        Order orderEntity = new Order();
        
        Product product = new Product();
        product.setProductId((short) 1);
        product.setProductName("Discontinued Product");
        product.setDiscontinued(1);

        when(orderMapper.toEntity(requestDTO)).thenReturn(orderEntity);
        when(productRepository.findById((short) 1)).thenReturn(Optional.of(product));

        // Act & Assert (When & Then)
        assertThrows(BadRequestException.class, () -> orderService.save(requestDTO));
        verify(orderRepository, never()).save(any());
    }

    @Test
    void save_WhenInsufficientStock_ShouldThrowInsufficientStockException() {
        // Arrange (Given)
        OrderRequestDTO requestDTO = new OrderRequestDTO();
        requestDTO.setDetails(List.of(new OrderDetailRequestDTO((short) 1, 10.0f, (short) 15, 0.0f)));
        
        Order orderEntity = new Order();
        
        Product product = new Product();
        product.setProductId((short) 1);
        product.setProductName("Low Stock Product");
        product.setDiscontinued(0);
        product.setUnitsInStock((short) 10);

        when(orderMapper.toEntity(requestDTO)).thenReturn(orderEntity);
        when(productRepository.findById((short) 1)).thenReturn(Optional.of(product));

        // Act & Assert (When & Then)
        assertThrows(InsufficientStockException.class, () -> orderService.save(requestDTO));
        verify(orderRepository, never()).save(any());
    }

    // --- shipOrder (Enviar orden) ---

    @Test
    void shipOrder_ShouldSetStatusToShippedAndAssignDate() {
        // Arrange (Given)
        short orderId = 1;
        Order order = new Order();
        order.setOrderId(orderId);
        order.setStatus(OrderStatus.PENDING);
        order.setShipper(new Shipper());

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
        when(orderRepository.save(order)).thenReturn(order);
        when(orderMapper.toResponseDTO(order)).thenReturn(new OrderResponseDTO());

        // Act (When)
        orderService.shipOrder(orderId);

        // Assert (Then)
        assertEquals(OrderStatus.SHIPPED, order.getStatus());
        assertNotNull(order.getShippedDate());
        verify(orderRepository).save(order);
    }

    @Test
    void shipOrder_WhenNotPending_ShouldThrowBadRequestException() {
        // Arrange (Given)
        short orderId = 1;
        Order order = new Order();
        order.setStatus(OrderStatus.CANCELLED);

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));

        // Act & Assert (When & Then)
        assertThrows(BadRequestException.class, () -> orderService.shipOrder(orderId));
    }

    @Test
    void shipOrder_WhenNoShipper_ShouldThrowBadRequestException() {
        // Arrange (Given)
        short orderId = 1;
        Order order = new Order();
        order.setStatus(OrderStatus.PENDING);
        order.setShipper(null);

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));

        // Act & Assert (When & Then)
        assertThrows(BadRequestException.class, () -> orderService.shipOrder(orderId));
    }

    // --- cancelOrder (Cancelar orden) ---

    @Test
    void cancelOrder_ShouldRestoreStockAndSetStatusCancelled() {
        // Arrange (Given)
        short orderId = 1;
        Order order = new Order();
        order.setOrderId(orderId);
        order.setStatus(OrderStatus.PENDING);
        
        Product product = new Product();
        product.setUnitsInStock((short) 10);
        product.setUnitsOnOrder((short) 5);

        OrderDetail detail = new OrderDetail();
        detail.setProduct(product);
        detail.setQuantity((short) 5);
        order.setDetails(List.of(detail));

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
        when(orderRepository.save(order)).thenReturn(order);
        when(orderMapper.toResponseDTO(order)).thenReturn(new OrderResponseDTO());

        // Act (When)
        orderService.cancelOrder(orderId);

        // Assert (Then)
        assertEquals(OrderStatus.CANCELLED, order.getStatus());
        assertEquals((short) 15, product.getUnitsInStock());
        assertEquals((short) 0, product.getUnitsOnOrder());
        verify(productRepository).save(product);
        verify(orderRepository).save(order);
    }

    @Test
    void cancelOrder_WhenAlreadyShipped_ShouldThrowConflictException() {
        // Arrange (Given)
        short orderId = 1;
        Order order = new Order();
        order.setStatus(OrderStatus.SHIPPED);

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));

        // Act & Assert (When & Then)
        assertThrows(ConflictException.class, () -> orderService.cancelOrder(orderId));
    }
}
