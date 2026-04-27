package org.ezequiel.proyectofinal.features.catalog.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "ProductUpdateRequestDTO", description = "Datos requeridos para actualizar un producto existente en el catálogo")
public class ProductUpdateRequestDTO {

    @NotBlank(message = "Product name is required")
    @Size(max = 40, message = "Product name must not exceed 40 characters")
    @Schema(
        description = "Nombre visible del producto",
        example = "Chai Premium",
        maxLength = 40,
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String productName;

    @Schema(
        description = "Identificador del proveedor asociado al producto",
        example = "3",
        nullable = true
    )
    private Short supplierId;

    @Schema(
        description = "Identificador de la categoría asociada al producto",
        example = "1",
        nullable = true
    )
    private Short categoryId;

    @Size(max = 20, message = "Quantity per unit must not exceed 20 characters")
    @Schema(
        description = "Descripción comercial de la cantidad o presentación por unidad",
        example = "10 boxes x 20 bags",
        maxLength = 20,
        nullable = true
    )
    private String quantityPerUnit;

    @Schema(
        description = "Precio unitario del producto",
        example = "19.5",
        nullable = true
    )
    private Float unitPrice;

    @Schema(
        description = "Nivel de reposición a partir del cual debe reabastecerse el producto",
        example = "12",
        defaultValue = "0",
        nullable = true
    )
    private Short reorderLevel = 0;
}
