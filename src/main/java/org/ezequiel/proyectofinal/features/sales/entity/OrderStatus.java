package org.ezequiel.proyectofinal.features.sales.entity;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Estados posibles de una orden en el sistema de ventas")
public enum OrderStatus {
    
    @Schema(description = "Orden creada pero pendiente de procesamiento")
    PENDING,
    
    @Schema(description = "Orden enviada al cliente")
    SHIPPED,
    
    @Schema(description = "Orden entregada exitosamente")
    DELIVERED,
    
    @Schema(description = "Orden cancelada por el cliente o sistema")
    CANCELLED
}
