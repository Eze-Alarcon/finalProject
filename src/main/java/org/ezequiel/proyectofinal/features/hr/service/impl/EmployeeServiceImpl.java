package org.ezequiel.proyectofinal.features.hr.service.impl;

import lombok.RequiredArgsConstructor;
import org.ezequiel.proyectofinal.core.exceptions.ResourceNotFoundException;
import org.ezequiel.proyectofinal.features.hr.dto.EmployeeRequestDTO;
import org.ezequiel.proyectofinal.features.hr.dto.EmployeeResponseDTO;
import org.ezequiel.proyectofinal.features.hr.entity.Employee;
import org.ezequiel.proyectofinal.features.hr.mapper.EmployeeMapper;
import org.ezequiel.proyectofinal.features.hr.repository.EmployeeRepository;
import org.ezequiel.proyectofinal.features.hr.service.EmployeeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;

    @Override
    public List<EmployeeResponseDTO> findAll() {
        return employeeRepository.findAllProjected();
    }

    @Override
    public EmployeeResponseDTO findById(Short id) {
        return employeeRepository.findByIdProjected(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee", id));
    }

    @Override
    @Transactional
    public EmployeeResponseDTO save(EmployeeRequestDTO dto) {
        Employee employee = employeeMapper.toEntity(dto);
        resolveReportsTo(employee, dto.getReportsToId());
        Employee saved = employeeRepository.save(employee);
        return employeeMapper.toResponseDTO(saved);
    }

    @Override
    @Transactional
    public EmployeeResponseDTO update(Short id, EmployeeRequestDTO dto) {
        Employee existing = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee", id));
        employeeMapper.updateEntityFromDTO(dto, existing);
        resolveReportsTo(existing, dto.getReportsToId());
        Employee updated = employeeRepository.save(existing);
        return employeeMapper.toResponseDTO(updated);
    }

    @Override
    @Transactional
    public void delete(Short id) {
        if (!employeeRepository.existsById(id)) {
            throw new ResourceNotFoundException("Employee", id);
        }
        employeeRepository.deleteById(id);
    }

    private void resolveReportsTo(Employee employee, Short reportsToId) {
        if (reportsToId != null) {
            Employee manager = employeeRepository.findById(reportsToId)
                    .orElseThrow(() -> new ResourceNotFoundException("Employee (manager)", reportsToId));
            employee.setReportsTo(manager);
        } else {
            employee.setReportsTo(null);
        }
    }
}
