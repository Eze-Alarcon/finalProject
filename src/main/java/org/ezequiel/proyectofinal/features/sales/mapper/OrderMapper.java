package org.ezequiel.proyectofinal.features.sales.mapper;

import org.ezequiel.proyectofinal.features.sales.dto.OrderRequestDTO;
import org.ezequiel.proyectofinal.features.sales.dto.OrderResponseDTO;
import org.ezequiel.proyectofinal.features.sales.entity.Order;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", uses = {OrderDetailMapper.class})
public interface OrderMapper {

    @Mapping(target = "customer", ignore = true)
    @Mapping(target = "employee", ignore = true)
    @Mapping(target = "shipper", ignore = true)
    @Mapping(target = "details", ignore = true)
    Order toEntity(OrderRequestDTO dto);

    @Mapping(target = "customerId", source = "customer.customerId")
    @Mapping(target = "employeeId", source = "employee.employeeId")
    @Mapping(target = "shipperId", source = "shipper.shipperId")
    OrderResponseDTO toResponseDTO(Order entity);

    @Mapping(target = "customerId", source = "customer.customerId")
    @Mapping(target = "employeeId", source = "employee.employeeId")
    @Mapping(target = "shipperId", source = "shipper.shipperId")
    OrderRequestDTO toRequestDTO(Order entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "customer", ignore = true)
    @Mapping(target = "employee", ignore = true)
    @Mapping(target = "shipper", ignore = true)
    @Mapping(target = "details", ignore = true)
    void updateEntityFromDTO(OrderRequestDTO dto, @MappingTarget Order entity);
}
