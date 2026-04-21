package org.ezequiel.proyectofinal.features.hr.service.impl;

import lombok.RequiredArgsConstructor;
import org.ezequiel.proyectofinal.core.exceptions.ResourceNotFoundException;
import org.ezequiel.proyectofinal.features.hr.dto.TerritoryRequestDTO;
import org.ezequiel.proyectofinal.features.hr.dto.TerritoryResponseDTO;
import org.ezequiel.proyectofinal.features.hr.dto.TerritoryUpdateRequestDTO;
import org.ezequiel.proyectofinal.features.hr.entity.Region;
import org.ezequiel.proyectofinal.features.hr.entity.Territory;
import org.ezequiel.proyectofinal.features.hr.mapper.TerritoryMapper;
import org.ezequiel.proyectofinal.features.hr.repository.RegionRepository;
import org.ezequiel.proyectofinal.features.hr.repository.TerritoryRepository;
import org.ezequiel.proyectofinal.features.hr.service.TerritoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TerritoryServiceImpl implements TerritoryService {

    private final TerritoryRepository territoryRepository;
    private final RegionRepository regionRepository;
    private final TerritoryMapper territoryMapper;

    @Override
    public List<TerritoryResponseDTO> findAll() {
        return territoryRepository.findAll()
                .stream()
                .map(territoryMapper::toResponseDTO)
                .toList();
    }

    @Override
    public TerritoryResponseDTO findById(String id) {
        Territory territory = territoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Territory", id));
        return territoryMapper.toResponseDTO(territory);
    }

    @Override
    @Transactional
    public TerritoryResponseDTO save(TerritoryRequestDTO dto) {
        Territory territory = territoryMapper.toEntity(dto);
        resolveRegion(territory, dto.getRegionId());
        Territory saved = territoryRepository.save(territory);
        return territoryMapper.toResponseDTO(saved);
    }

    @Override
    @Transactional
    public TerritoryResponseDTO update(String id, TerritoryUpdateRequestDTO dto) {
        Territory existing = territoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Territory", id));
        var territory = new TerritoryRequestDTO();
        territory.setTerritoryId(id);
        territory.setTerritoryDescription(dto.getTerritoryDescription());
        territory.setRegionId(dto.getRegionId());
        territoryMapper.updateEntityFromDTO(territory, existing);
        resolveRegion(existing, dto.getRegionId());
        Territory updated = territoryRepository.save(existing);
        return territoryMapper.toResponseDTO(updated);
    }

    @Override
    @Transactional
    public void delete(String id) {
        if (!territoryRepository.existsById(id)) {
            throw new ResourceNotFoundException("Territory", id);
        }
        territoryRepository.deleteById(id);
    }

    private void resolveRegion(Territory territory, Short regionId) {
        Region region = regionRepository.findById(regionId)
                .orElseThrow(() -> new ResourceNotFoundException("Region", regionId));
        territory.setRegion(region);
    }
}
