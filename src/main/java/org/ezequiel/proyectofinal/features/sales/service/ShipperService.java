package org.ezequiel.proyectofinal.features.sales.service;

import org.ezequiel.proyectofinal.features.sales.dto.ShipperRequestDTO;
import org.ezequiel.proyectofinal.features.sales.dto.ShipperResponseDTO;

import java.util.List;

public interface ShipperService {

    List<ShipperResponseDTO> findAll();

    ShipperResponseDTO findById(Short id);

    ShipperResponseDTO save(ShipperRequestDTO dto);

    ShipperResponseDTO update(Short id, ShipperRequestDTO dto);

    void delete(Short id);
}
