package org.ezequiel.proyectofinal.features.catalog.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponseDTO {

    private Short productId;
    private String productName;
    private Short supplierId;
    private String supplierName;
    private Short categoryId;
    private String categoryName;
    private String quantityPerUnit;
    private Float unitPrice;
    private Short unitsInStock;
    private Short unitsOnOrder;
    private Short reorderLevel;
    private Integer discontinued;
}
