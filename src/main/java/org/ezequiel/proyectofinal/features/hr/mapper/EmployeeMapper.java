package org.ezequiel.proyectofinal.features.hr.mapper;

import org.ezequiel.proyectofinal.features.hr.dto.EmployeeRequestDTO;
import org.ezequiel.proyectofinal.features.hr.dto.EmployeeResponseDTO;
import org.ezequiel.proyectofinal.features.hr.entity.Employee;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {

    @Mapping(target = "reportsTo", ignore = true)
    @Mapping(target = "subordinates", ignore = true)
    @Mapping(target = "employeeTerritories", ignore = true)
    @Mapping(target = "photo", ignore = true)
    Employee toEntity(EmployeeRequestDTO dto);

    @Mapping(target = "reportsToId", source = "reportsTo.employeeId")
    @Mapping(target = "reportsToFullName", expression = "java(entity.getReportsTo() != null ? entity.getReportsTo().getFirstName() + ' ' + entity.getReportsTo().getLastName() : null)")
    EmployeeResponseDTO toResponseDTO(Employee entity);

    @Mapping(target = "reportsToId", source = "reportsTo.employeeId")
    EmployeeRequestDTO toRequestDTO(Employee entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "reportsTo", ignore = true)
    @Mapping(target = "subordinates", ignore = true)
    @Mapping(target = "employeeTerritories", ignore = true)
    @Mapping(target = "photo", ignore = true)
    void updateEntityFromDTO(EmployeeRequestDTO dto, @MappingTarget Employee entity);
}
