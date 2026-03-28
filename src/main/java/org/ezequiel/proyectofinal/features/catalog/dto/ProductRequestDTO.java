package org.ezequiel.proyectofinal.features.catalog.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequestDTO {

    @NotBlank(message = "Product name is required")
    @Size(max = 40, message = "Product name must not exceed 40 characters")
    private String productName;

    private Short supplierId;

    private Short categoryId;

    @Size(max = 20, message = "Quantity per unit must not exceed 20 characters")
    private String quantityPerUnit;

    private Float unitPrice;

    private Short unitsInStock;

    private Short unitsOnOrder;

    private Short reorderLevel;

    @NotNull(message = "Discontinued flag is required")
    private Integer discontinued;
}
