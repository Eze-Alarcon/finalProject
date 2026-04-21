package org.ezequiel.proyectofinal.features.hr.service.impl;

import lombok.RequiredArgsConstructor;
import org.ezequiel.proyectofinal.core.exceptions.ResourceNotFoundException;
import org.ezequiel.proyectofinal.features.hr.dto.EmployeeTerritoryRequestDTO;
import org.ezequiel.proyectofinal.features.hr.dto.EmployeeTerritoryResponseDTO;
import org.ezequiel.proyectofinal.features.hr.entity.Employee;
import org.ezequiel.proyectofinal.features.hr.entity.EmployeeTerritory;
import org.ezequiel.proyectofinal.features.hr.entity.EmployeeTerritoryId;
import org.ezequiel.proyectofinal.features.hr.entity.Territory;
import org.ezequiel.proyectofinal.features.hr.mapper.EmployeeTerritoryMapper;
import org.ezequiel.proyectofinal.features.hr.repository.EmployeeRepository;
import org.ezequiel.proyectofinal.features.hr.repository.EmployeeTerritoryRepository;
import org.ezequiel.proyectofinal.features.hr.repository.TerritoryRepository;
import org.ezequiel.proyectofinal.features.hr.service.EmployeeTerritoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EmployeeTerritoryServiceImpl implements EmployeeTerritoryService {

    private final EmployeeTerritoryRepository employeeTerritoryRepository;
    private final EmployeeRepository employeeRepository;
    private final TerritoryRepository territoryRepository;
    private final EmployeeTerritoryMapper employeeTerritoryMapper;

    @Override
    public List<EmployeeTerritoryResponseDTO> findAll() {
        return employeeTerritoryRepository.findAllProjected().stream()
                .map(employeeTerritoryMapper::toResponseDTO)
                .toList();
    }

    @Override
    public EmployeeTerritoryResponseDTO findByEmployeeId(Short employeeId) {
        return employeeTerritoryRepository.findByEmployeeIdProjected(employeeId)
                .map(employeeTerritoryMapper::toResponseDTO)
                .orElseThrow(() -> new ResourceNotFoundException("EmployeeTerritory details for employee", employeeId));
    }

    @Override
    @Transactional
    public EmployeeTerritoryResponseDTO save(EmployeeTerritoryRequestDTO dto) {
        Employee employee = employeeRepository.findById(dto.getEmployeeId())
                .orElseThrow(() -> new ResourceNotFoundException("Employee", dto.getEmployeeId()));
        Territory territory = territoryRepository.findById(dto.getTerritoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Territory", dto.getTerritoryId()));

        EmployeeTerritoryId id = new EmployeeTerritoryId(dto.getEmployeeId(), dto.getTerritoryId());
        EmployeeTerritory employeeTerritory = new EmployeeTerritory(id, employee, territory);
        EmployeeTerritory saved = employeeTerritoryRepository.save(employeeTerritory);
        return employeeTerritoryMapper.toResponseDTO(saved);
    }

    @Override
    @Transactional
    public void delete(Short employeeId, String territoryId) {
        EmployeeTerritoryId id = new EmployeeTerritoryId(employeeId, territoryId);
        if (!employeeTerritoryRepository.existsById(id)) {
            throw new ResourceNotFoundException(
                    String.format("EmployeeTerritory with employeeId '%s' and territoryId '%s' not found",
                            employeeId, territoryId));
        }
        employeeTerritoryRepository.deleteById(id);
    }
}
