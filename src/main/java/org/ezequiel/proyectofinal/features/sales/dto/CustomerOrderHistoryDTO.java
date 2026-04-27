package org.ezequiel.proyectofinal.features.sales.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.ezequiel.proyectofinal.features.sales.entity.OrderStatus;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Información resumida de una orden en el historial del cliente")
public class CustomerOrderHistoryDTO {

    @Schema(
            description = "Identificador único de la orden",
            example = "10248"
    )
    private Short orderId;

    @Schema(
            description = "Fecha en que se realizó la orden",
            example = "2024-01-15",
            type = "string",
            format = "date"
    )
    private LocalDate orderDate;

    @Schema(
            description = "Estado actual de la orden",
            example = "SHIPPED",
            enumAsRef = true
    )
    private OrderStatus status;

    @Schema(
            description = "Monto total de la orden en la moneda del sistema",
            example = "440.00"
    )
    private Double totalAmount;
}
