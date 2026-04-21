package org.ezequiel.proyectofinal.features.catalog.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductLowStockResponseDTO {
    private Short id;
    private String name;
    private Short currentStock;
    private Short reorderLevel;
    private String supplierName;
}
