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
@Schema(name = "ProductResponseDTO", description = "Información completa de un producto del catálogo")
public class ProductResponseDTO {

    @Schema(
        description = "Identificador único del producto",
        example = "1"
    )
    private Short productId;

    @Schema(
        description = "Nombre visible del producto",
        example = "Chai"
    )
    private String productName;

    @Schema(
        description = "Identificador del proveedor asociado al producto",
        example = "3",
        nullable = true
    )
    private Short supplierId;

    @Schema(
        description = "Nombre del proveedor asociado al producto",
        example = "Exotic Liquids",
        nullable = true
    )
    private String supplierName;

    @Schema(
        description = "Identificador de la categoría asociada al producto",
        example = "1",
        nullable = true
    )
    private Short categoryId;

    @Schema(
        description = "Nombre de la categoría asociada al producto",
        example = "Beverages",
        nullable = true
    )
    private String categoryName;

    @Schema(
        description = "Descripción comercial de la cantidad o presentación por unidad",
        example = "10 boxes x 20 bags",
        nullable = true
    )
    private String quantityPerUnit;

    @Schema(
        description = "Precio unitario del producto",
        example = "18.0",
        nullable = true
    )
    private Float unitPrice;

    @Schema(
        description = "Cantidad de unidades actualmente disponibles",
        example = "39",
        nullable = true
    )
    private Short unitsInStock;

    @Schema(
        description = "Cantidad de unidades actualmente reservadas o pedidas",
        example = "0",
        nullable = true
    )
    private Short unitsOnOrder;

    @Schema(
        description = "Nivel de reposición configurado para el producto",
        example = "10",
        nullable = true
    )
    private Short reorderLevel;
    // private Integer discontinued;
}
