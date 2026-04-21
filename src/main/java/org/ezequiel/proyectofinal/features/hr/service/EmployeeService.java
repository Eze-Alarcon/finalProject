package org.ezequiel.proyectofinal.features.hr.service;

import org.ezequiel.proyectofinal.features.hr.dto.EmployeeRequestDTO;
import org.ezequiel.proyectofinal.features.hr.dto.EmployeeResponseDTO;

import java.util.List;

public interface EmployeeService {

    List<EmployeeResponseDTO> findAll();

    EmployeeResponseDTO findById(Short id);

    EmployeeResponseDTO save(EmployeeRequestDTO dto);

    EmployeeResponseDTO update(Short id, EmployeeRequestDTO dto);

    void delete(Short id);
}
