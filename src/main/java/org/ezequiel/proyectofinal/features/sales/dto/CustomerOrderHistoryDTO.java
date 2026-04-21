package org.ezequiel.proyectofinal.features.sales.dto;

import lombok.*;
import org.ezequiel.proyectofinal.features.sales.entity.OrderStatus;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerOrderHistoryDTO {
    private Short orderId;
    private LocalDate orderDate;
    private OrderStatus status;
    private Double totalAmount;
}
