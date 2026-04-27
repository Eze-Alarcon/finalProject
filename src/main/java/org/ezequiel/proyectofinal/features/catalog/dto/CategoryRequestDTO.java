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
@Schema(name = "CategoryRequestDTO", description = "Datos requeridos para crear o actualizar una categoría de productos del catálogo")
public class CategoryRequestDTO {


    @NotBlank(message = "Category name is required")
    @Size(max = 15, message = "Category name must not exceed 15 characters")
    @Schema(
        description = "Nombre visible de la categoría de producto",
        example = "Beverages",
        maxLength = 15,
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String categoryName;

    @Schema(
        description = "Descripción opcional de la categoría para consumo de la API",
        example = "Productos de bebidas y refrescos"
    )
    private String description;

    @Schema(
        description = "Imagen opcional de la categoría en formato binario. No se asume almacenamiento ni estructura adicional en el código actual.",
        type = "string",
        format = "byte",
        nullable = true,
        example = ""
    )
    private byte[] picture;
}
