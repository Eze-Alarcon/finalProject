package org.ezequiel.proyectofinal.features.sales.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailResponseDTO {

    private Short orderId;
    private Short productId;
    private String productName;
    private Float unitPrice;
    private Short quantity;
    private Float discount;
}
