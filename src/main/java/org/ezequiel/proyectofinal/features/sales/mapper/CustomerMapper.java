package org.ezequiel.proyectofinal.features.sales.mapper;

import org.ezequiel.proyectofinal.features.sales.dto.CustomerRequestDTO;
import org.ezequiel.proyectofinal.features.sales.dto.CustomerResponseDTO;
import org.ezequiel.proyectofinal.features.sales.entity.Customer;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    Customer toEntity(CustomerRequestDTO dto);

    CustomerResponseDTO toResponseDTO(Customer entity);

    CustomerRequestDTO toRequestDTO(Customer entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDTO(CustomerRequestDTO dto, @MappingTarget Customer entity);
}
