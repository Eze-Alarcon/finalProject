package org.ezequiel.proyectofinal.features.hr.service.impl;

import lombok.RequiredArgsConstructor;
import org.ezequiel.proyectofinal.core.exceptions.ResourceNotFoundException;
import org.ezequiel.proyectofinal.features.hr.dto.UsStateRequestDTO;
import org.ezequiel.proyectofinal.features.hr.dto.UsStateResponseDTO;
import org.ezequiel.proyectofinal.features.hr.entity.UsState;
import org.ezequiel.proyectofinal.features.hr.mapper.UsStateMapper;
import org.ezequiel.proyectofinal.features.hr.repository.UsStateRepository;
import org.ezequiel.proyectofinal.features.hr.service.UsStateService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UsStateServiceImpl implements UsStateService {

    private final UsStateRepository usStateRepository;
    private final UsStateMapper usStateMapper;

    @Override
    public List<UsStateResponseDTO> findAll() {
        return usStateRepository.findAll()
                .stream()
                .map(usStateMapper::toResponseDTO)
                .toList();
    }

    @Override
    public UsStateResponseDTO findById(Short id) {
        UsState usState = usStateRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("UsState", id));
        return usStateMapper.toResponseDTO(usState);
    }

    @Override
    @Transactional
    public UsStateResponseDTO save(UsStateRequestDTO dto) {
        UsState usState = usStateMapper.toEntity(dto);
        UsState saved = usStateRepository.save(usState);
        return usStateMapper.toResponseDTO(saved);
    }

    @Override
    @Transactional
    public UsStateResponseDTO update(Short id, UsStateRequestDTO dto) {
        UsState existing = usStateRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("UsState", id));
        usStateMapper.updateEntityFromDTO(dto, existing);
        UsState updated = usStateRepository.save(existing);
        return usStateMapper.toResponseDTO(updated);
    }

    @Override
    @Transactional
    public void delete(Short id) {
        if (!usStateRepository.existsById(id)) {
            throw new ResourceNotFoundException("UsState", id);
        }
        usStateRepository.deleteById(id);
    }
}
