package org.ezequiel.proyectofinal.features.catalog.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "ProductLowStockResponseDTO", description = "Resumen de un producto detectado con stock bajo")
public class ProductLowStockResponseDTO {

    @Schema(
        description = "Identificador único del producto",
        example = "1"
    )
    private Short id;

    @Schema(
        description = "Nombre visible del producto",
        example = "Chai"
    )
    private String name;

    @Schema(
        description = "Cantidad actual disponible del producto en el sistema",
        example = "4"
    )
    private Short currentStock;

    @Schema(
        description = "Nivel de reposición configurado para el producto",
        example = "10"
    )
    private Short reorderLevel;

    @Schema(
        description = "Nombre del proveedor asociado al producto, cuando está disponible en el modelo de salida",
        example = "Exotic Liquids",
        nullable = true
    )
    private String supplierName;
}
