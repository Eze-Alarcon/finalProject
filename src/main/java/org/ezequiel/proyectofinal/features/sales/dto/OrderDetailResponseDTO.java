package org.ezequiel.proyectofinal.features.sales.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Información detallada de un producto incluido en una orden de venta")
public class OrderDetailResponseDTO {

    @Schema(
            description = "ID de la orden a la que pertenece este detalle",
            example = "10248"
    )
    private Short orderId;
    
    @Schema(
            description = "ID del producto incluido en la orden",
            example = "11"
    )
    private Short productId;
    
    @Schema(
            description = "Nombre del producto para referencia rápida",
            example = "Queso Cabrales"
    )
    private String productName;
    
    @Schema(
            description = "Precio unitario del producto al momento de la orden",
            example = "14.00"
    )
    private Float unitPrice;
    
    @Schema(
            description = "Cantidad de unidades del producto en la orden",
            example = "12"
    )
    private Short quantity;
    
    @Schema(
            description = "Descuento aplicado (0.0 a 1.0, donde 0.1 representa 10% de descuento)",
            example = "0.00"
    )
    private Float discount;
}
