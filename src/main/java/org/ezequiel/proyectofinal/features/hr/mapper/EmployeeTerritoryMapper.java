package org.ezequiel.proyectofinal.features.hr.mapper;

import org.ezequiel.proyectofinal.features.hr.dto.EmployeeTerritoryRequestDTO;
import org.ezequiel.proyectofinal.features.hr.dto.EmployeeTerritoryResponseDTO;
import org.ezequiel.proyectofinal.features.hr.entity.EmployeeTerritory;
import org.ezequiel.proyectofinal.features.hr.repository.EmployeeTerritoryProjection;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EmployeeTerritoryMapper {

    @Mapping(target = "employeeId", source = "employee.employeeId")
    @Mapping(target = "employeeFullName", expression = "java(entity.getEmployee().getFirstName() + ' ' + entity.getEmployee().getLastName())")
    @Mapping(target = "territories", source = "territory.territoryDescription")
    EmployeeTerritoryResponseDTO toResponseDTO(EmployeeTerritory entity);

    EmployeeTerritoryResponseDTO toResponseDTO(EmployeeTerritoryProjection projection);

    @Mapping(target = "employeeId", source = "id.employeeId")
    @Mapping(target = "territoryId", source = "id.territoryId")
    EmployeeTerritoryRequestDTO toRequestDTO(EmployeeTerritory entity);
}
