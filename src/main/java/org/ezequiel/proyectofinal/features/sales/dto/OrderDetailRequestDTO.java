package org.ezequiel.proyectofinal.features.sales.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailRequestDTO {

    @NotNull(message = "Product ID is required")
    private Short productId;

    @NotNull(message = "Unit price is required")
    private Float unitPrice;

    @NotNull(message = "Quantity is required")
    private Short quantity;

    @NotNull(message = "Discount is required")
    private Float discount;
}
