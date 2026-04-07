package org.ezequiel.proyectofinal.features.sales.service.impl;

import lombok.RequiredArgsConstructor;
import org.ezequiel.proyectofinal.core.exceptions.ResourceNotFoundException;
import org.ezequiel.proyectofinal.features.sales.dto.CustomerOrderHistoryDTO;
import org.ezequiel.proyectofinal.features.sales.dto.CustomerRequestDTO;
import org.ezequiel.proyectofinal.features.sales.dto.CustomerResponseDTO;
import org.ezequiel.proyectofinal.features.sales.entity.Customer;
import org.ezequiel.proyectofinal.features.sales.mapper.CustomerMapper;
import org.ezequiel.proyectofinal.features.sales.repository.CustomerRepository;
import org.ezequiel.proyectofinal.features.sales.repository.OrderRepository;
import org.ezequiel.proyectofinal.features.sales.service.CustomerService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final OrderRepository orderRepository;
    private final CustomerMapper customerMapper;

    @Override
    public List<CustomerResponseDTO> findAll() {
        return customerRepository.findAll()
                .stream()
                .map(customerMapper::toResponseDTO)
                .toList();
    }

    @Override
    public CustomerResponseDTO findById(String id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", id));
        return customerMapper.toResponseDTO(customer);
    }

    @Override
    @Transactional
    public CustomerResponseDTO save(CustomerRequestDTO dto) {
        Customer customer = customerMapper.toEntity(dto);
        Customer saved = customerRepository.save(customer);
        return customerMapper.toResponseDTO(saved);
    }

    @Override
    @Transactional
    public CustomerResponseDTO update(String id, CustomerRequestDTO dto) {
        Customer existing = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", id));
        customerMapper.updateEntityFromDTO(dto, existing);
        Customer updated = customerRepository.save(existing);
        return customerMapper.toResponseDTO(updated);
    }

    @Override
    @Transactional
    public void delete(String id) {
        if (!customerRepository.existsById(id)) {
            throw new ResourceNotFoundException("Customer", id);
        }
        customerRepository.deleteById(id);
    }

    @Override
    public Page<CustomerOrderHistoryDTO> getOrderHistory(String customerId, Pageable pageable) {
        if (!customerRepository.existsById(customerId)) {
            throw new ResourceNotFoundException("Customer", customerId);
        }
        return orderRepository.findOrderHistoryByCustomerId(customerId, pageable);
    }
}
