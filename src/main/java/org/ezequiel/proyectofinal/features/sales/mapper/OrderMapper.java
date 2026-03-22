package org.ezequiel.proyectofinal.features.sales.mapper;

import org.ezequiel.proyectofinal.features.sales.dto.OrderRequestDTO;
import org.ezequiel.proyectofinal.features.sales.dto.OrderResponseDTO;
import org.ezequiel.proyectofinal.features.sales.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    @Mapping(target = "customer", ignore = true)
    @Mapping(target = "employee", ignore = true)
    @Mapping(target = "shipper", ignore = true)
    Order toEntity(OrderRequestDTO dto);

    @Mapping(target = "customerId", source = "customer.customerId")
    @Mapping(target = "employeeId", source = "employee.employeeId")
    @Mapping(target = "shipperId", source = "shipper.shipperId")
    OrderResponseDTO toResponseDTO(Order entity);

    @Mapping(target = "customerId", source = "customer.customerId")
    @Mapping(target = "employeeId", source = "employee.employeeId")
    @Mapping(target = "shipperId", source = "shipper.shipperId")
    OrderRequestDTO toRequestDTO(Order entity);

    @Mapping(target = "customer", ignore = true)
    @Mapping(target = "employee", ignore = true)
    @Mapping(target = "shipper", ignore = true)
    void updateEntityFromDTO(OrderRequestDTO dto, @MappingTarget Order entity);
}
