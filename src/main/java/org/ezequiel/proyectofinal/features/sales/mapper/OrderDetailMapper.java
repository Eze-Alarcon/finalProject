package org.ezequiel.proyectofinal.features.sales.mapper;

import org.ezequiel.proyectofinal.features.sales.dto.OrderDetailRequestDTO;
import org.ezequiel.proyectofinal.features.sales.dto.OrderDetailResponseDTO;
import org.ezequiel.proyectofinal.features.sales.entity.OrderDetail;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface OrderDetailMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "order", ignore = true)
    @Mapping(target = "product", ignore = true)
    OrderDetail toEntity(OrderDetailRequestDTO dto);

    @Mapping(target = "orderId", source = "order.orderId")
    @Mapping(target = "productId", source = "product.productId")
    OrderDetailResponseDTO toResponseDTO(OrderDetail entity);

    @Mapping(target = "orderId", source = "id.orderId")
    @Mapping(target = "productId", source = "id.productId")
    OrderDetailRequestDTO toRequestDTO(OrderDetail entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "order", ignore = true)
    @Mapping(target = "product", ignore = true)
    void updateEntityFromDTO(OrderDetailRequestDTO dto, @MappingTarget OrderDetail entity);
}
