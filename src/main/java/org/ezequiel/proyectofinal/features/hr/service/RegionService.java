package org.ezequiel.proyectofinal.features.hr.service;

import org.ezequiel.proyectofinal.features.hr.dto.RegionRequestDTO;
import org.ezequiel.proyectofinal.features.hr.dto.RegionResponseDTO;

import java.util.List;

public interface RegionService {

    List<RegionResponseDTO> findAll();

    RegionResponseDTO findById(Short id);

    RegionResponseDTO save(RegionRequestDTO dto);

    RegionResponseDTO update(Short id, RegionRequestDTO dto);

    void delete(Short id);
}
