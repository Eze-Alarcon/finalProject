package org.ezequiel.proyectofinal.features.hr.service;

import org.ezequiel.proyectofinal.features.hr.dto.EmployeeTerritoryRequestDTO;
import org.ezequiel.proyectofinal.features.hr.dto.EmployeeTerritoryResponseDTO;

import java.util.List;

public interface EmployeeTerritoryService {

    List<EmployeeTerritoryResponseDTO> findAll();

    EmployeeTerritoryResponseDTO findByEmployeeId(Short employeeId);

    EmployeeTerritoryResponseDTO save(EmployeeTerritoryRequestDTO dto);

    void delete(Short employeeId, String territoryId);
}
