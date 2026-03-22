package org.ezequiel.proyectofinal.features.hr.service.impl;

import lombok.RequiredArgsConstructor;
import org.ezequiel.proyectofinal.core.exceptions.ResourceNotFoundException;
import org.ezequiel.proyectofinal.features.hr.dto.RegionRequestDTO;
import org.ezequiel.proyectofinal.features.hr.dto.RegionResponseDTO;
import org.ezequiel.proyectofinal.features.hr.entity.Region;
import org.ezequiel.proyectofinal.features.hr.mapper.RegionMapper;
import org.ezequiel.proyectofinal.features.hr.repository.RegionRepository;
import org.ezequiel.proyectofinal.features.hr.service.RegionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RegionServiceImpl implements RegionService {

    private final RegionRepository regionRepository;
    private final RegionMapper regionMapper;

    @Override
    public List<RegionResponseDTO> findAll() {
        return regionRepository.findAll()
                .stream()
                .map(regionMapper::toResponseDTO)
                .toList();
    }

    @Override
    public RegionResponseDTO findById(Short id) {
        Region region = regionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Region", id));
        return regionMapper.toResponseDTO(region);
    }

    @Override
    @Transactional
    public RegionResponseDTO save(RegionRequestDTO dto) {
        Region region = regionMapper.toEntity(dto);
        Region saved = regionRepository.save(region);
        return regionMapper.toResponseDTO(saved);
    }

    @Override
    @Transactional
    public RegionResponseDTO update(Short id, RegionRequestDTO dto) {
        Region existing = regionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Region", id));
        regionMapper.updateEntityFromDTO(dto, existing);
        Region updated = regionRepository.save(existing);
        return regionMapper.toResponseDTO(updated);
    }

    @Override
    @Transactional
    public void delete(Short id) {
        if (!regionRepository.existsById(id)) {
            throw new ResourceNotFoundException("Region", id);
        }
        regionRepository.deleteById(id);
    }
}
