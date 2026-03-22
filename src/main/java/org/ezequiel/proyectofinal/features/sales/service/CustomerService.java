package org.ezequiel.proyectofinal.features.sales.service;

import org.ezequiel.proyectofinal.features.sales.dto.CustomerRequestDTO;
import org.ezequiel.proyectofinal.features.sales.dto.CustomerResponseDTO;

import java.util.List;

public interface CustomerService {

    List<CustomerResponseDTO> findAll();

    CustomerResponseDTO findById(String id);

    CustomerResponseDTO save(CustomerRequestDTO dto);

    CustomerResponseDTO update(String id, CustomerRequestDTO dto);

    void delete(String id);
}
