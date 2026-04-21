package org.ezequiel.proyectofinal.features.hr.service;

import org.ezequiel.proyectofinal.features.hr.dto.UsStateRequestDTO;
import org.ezequiel.proyectofinal.features.hr.dto.UsStateResponseDTO;

import java.util.List;

public interface UsStateService {

    List<UsStateResponseDTO> findAll();

    UsStateResponseDTO findById(Short id);

    UsStateResponseDTO save(UsStateRequestDTO dto);

    UsStateResponseDTO update(Short id, UsStateRequestDTO dto);

    void delete(Short id);
}
