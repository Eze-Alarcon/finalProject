package org.ezequiel.proyectofinal.features.catalog.service.impl;

import lombok.RequiredArgsConstructor;
import org.ezequiel.proyectofinal.core.exceptions.ResourceNotFoundException;
import org.ezequiel.proyectofinal.features.catalog.dto.SupplierRequestDTO;
import org.ezequiel.proyectofinal.features.catalog.dto.SupplierResponseDTO;
import org.ezequiel.proyectofinal.features.catalog.entity.Supplier;
import org.ezequiel.proyectofinal.features.catalog.mapper.SupplierMapper;
import org.ezequiel.proyectofinal.features.catalog.repository.SupplierRepository;
import org.ezequiel.proyectofinal.features.catalog.service.SupplierService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SupplierServiceImpl implements SupplierService {

    private final SupplierRepository supplierRepository;
    private final SupplierMapper supplierMapper;

    @Override
    public List<SupplierResponseDTO> findAll() {
        return supplierRepository.findAll()
                .stream()
                .map(supplierMapper::toResponseDTO)
                .toList();
    }

    @Override
    public SupplierResponseDTO findById(Short id) {
        Supplier supplier = supplierRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Supplier", id));
        return supplierMapper.toResponseDTO(supplier);
    }

    @Override
    @Transactional
    public SupplierResponseDTO save(SupplierRequestDTO dto) {
        Supplier supplier = supplierMapper.toEntity(dto);
        Supplier saved = supplierRepository.save(supplier);
        return supplierMapper.toResponseDTO(saved);
    }

    @Override
    @Transactional
    public SupplierResponseDTO update(Short id, SupplierRequestDTO dto) {
        Supplier existing = supplierRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Supplier", id));
        supplierMapper.updateEntityFromDTO(dto, existing);
        Supplier updated = supplierRepository.save(existing);
        return supplierMapper.toResponseDTO(updated);
    }

    @Override
    @Transactional
    public void delete(Short id) {
        if (!supplierRepository.existsById(id)) {
            throw new ResourceNotFoundException("Supplier", id);
        }
        supplierRepository.deleteById(id);
    }
}
