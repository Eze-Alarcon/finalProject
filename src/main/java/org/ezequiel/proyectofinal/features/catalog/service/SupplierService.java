package org.ezequiel.proyectofinal.features.catalog.service;

import org.ezequiel.proyectofinal.features.catalog.dto.SupplierRequestDTO;
import org.ezequiel.proyectofinal.features.catalog.dto.SupplierResponseDTO;

import java.util.List;

public interface SupplierService {

    List<SupplierResponseDTO> findAll();

    SupplierResponseDTO findById(Short id);

    SupplierResponseDTO save(SupplierRequestDTO dto);

    SupplierResponseDTO update(Short id, SupplierRequestDTO dto);

    void delete(Short id);
}
