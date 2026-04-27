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
@Schema(name = "CategoryResponseDTO", description = "Información completa de una categoría de productos del catálogo")
public class CategoryResponseDTO {

    @Schema(
        description = "Identificador único de la categoría",
        example = "1"
    )
    private Short categoryId;

    @Schema(
        description = "Nombre visible de la categoría de producto",
        example = "Beverages"
    )
    private String categoryName;

    @Schema(
        description = "Descripción opcional de la categoría",
        example = "Productos de bebidas y refrescos"
    )
    private String description;
}
