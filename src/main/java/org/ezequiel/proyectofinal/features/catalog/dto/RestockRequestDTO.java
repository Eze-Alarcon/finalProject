package org.ezequiel.proyectofinal.features.catalog.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "RestockRequestDTO", description = "Datos requeridos para incrementar el stock disponible de un producto")
public class RestockRequestDTO {

    @NotNull(message = "Quantity is required")
    @Min(value = 1, message = "Quantity must be greater than 0")
    @Schema(
            description = "Cantidad de unidades a sumar al inventario del producto",
            example = "25",
            minimum = "1",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private Integer quantity;
}
