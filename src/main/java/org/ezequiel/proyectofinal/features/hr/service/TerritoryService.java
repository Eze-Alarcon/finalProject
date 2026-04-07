package org.ezequiel.proyectofinal.features.hr.service;

import org.ezequiel.proyectofinal.features.hr.dto.TerritoryRequestDTO;
import org.ezequiel.proyectofinal.features.hr.dto.TerritoryResponseDTO;
import org.ezequiel.proyectofinal.features.hr.dto.TerritoryUpdateRequestDTO;

import java.util.List;

public interface TerritoryService {

    List<TerritoryResponseDTO> findAll();

    TerritoryResponseDTO findById(String id);

    TerritoryResponseDTO save(TerritoryRequestDTO dto);

    TerritoryResponseDTO update(String id, TerritoryUpdateRequestDTO dto);

    void delete(String id);
}
