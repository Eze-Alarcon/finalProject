package org.ezequiel.proyectofinal.features.sales.service.impl;

import lombok.RequiredArgsConstructor;
import org.ezequiel.proyectofinal.core.exceptions.ResourceNotFoundException;
import org.ezequiel.proyectofinal.features.sales.dto.ShipperRequestDTO;
import org.ezequiel.proyectofinal.features.sales.dto.ShipperResponseDTO;
import org.ezequiel.proyectofinal.features.sales.entity.Shipper;
import org.ezequiel.proyectofinal.features.sales.mapper.ShipperMapper;
import org.ezequiel.proyectofinal.features.sales.repository.ShipperRepository;
import org.ezequiel.proyectofinal.features.sales.service.ShipperService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ShipperServiceImpl implements ShipperService {

    private final ShipperRepository shipperRepository;
    private final ShipperMapper shipperMapper;

    @Override
    public List<ShipperResponseDTO> findAll() {
        return shipperRepository.findAll()
                .stream()
                .map(shipperMapper::toResponseDTO)
                .toList();
    }

    @Override
    public ShipperResponseDTO findById(Short id) {
        Shipper shipper = shipperRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Shipper", id));
        return shipperMapper.toResponseDTO(shipper);
    }

    @Override
    @Transactional
    public ShipperResponseDTO save(ShipperRequestDTO dto) {
        Shipper shipper = shipperMapper.toEntity(dto);
        Shipper saved = shipperRepository.save(shipper);
        return shipperMapper.toResponseDTO(saved);
    }

    @Override
    @Transactional
    public ShipperResponseDTO update(Short id, ShipperRequestDTO dto) {
        Shipper existing = shipperRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Shipper", id));
        shipperMapper.updateEntityFromDTO(dto, existing);
        Shipper updated = shipperRepository.save(existing);
        return shipperMapper.toResponseDTO(updated);
    }

    @Override
    @Transactional
    public void delete(Short id) {
        if (!shipperRepository.existsById(id)) {
            throw new ResourceNotFoundException("Shipper", id);
        }
        shipperRepository.deleteById(id);
    }
}
