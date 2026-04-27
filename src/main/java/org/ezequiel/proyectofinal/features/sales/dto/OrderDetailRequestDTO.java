package org.ezequiel.proyectofinal.features.sales.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Detalle de producto individual dentro de una orden de venta")
public class OrderDetailRequestDTO {

    @Schema(
            description = "ID del producto a incluir en la orden (debe existir en el catálogo)",
            example = "11",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotNull(message = "Product ID is required")
    private Short productId;

    @Schema(
            description = "Precio unitario del producto al momento de la orden",
            example = "14.00",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotNull(message = "Unit price is required")
    private Float unitPrice;

    @Schema(
            description = "Cantidad de unidades del producto solicitadas",
            example = "12",
            minimum = "1",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotNull(message = "Quantity is required")
    private Short quantity;

    @Schema(
            description = "Descuento aplicado al producto (valor entre 0.0 y 1.0, donde 0.1 = 10%)",
            example = "0.00",
            minimum = "0.0",
            maximum = "1.0",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotNull(message = "Discount is required")
    private Float discount;
}
