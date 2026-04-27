package org.ezequiel.proyectofinal.features.sales.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Información completa de una orden de venta del sistema")
public class OrderResponseDTO {

    @Schema(
            description = "Identificador único de la orden en el sistema",
            example = "10248"
    )
    private Short orderId;

    @Schema(
            description = "ID del cliente que realizó la orden",
            example = "VINET"
    )
    private String customerId;

    @Schema(
            description = "ID del empleado asignado para gestionar la orden",
            example = "5"
    )
    private Short employeeId;

    @Schema(
            description = "Fecha en que se realizó la orden",
            example = "2024-03-15",
            type = "string",
            format = "date"
    )
    private LocalDate orderDate;

    @Schema(
            description = "Fecha límite requerida por el cliente para la entrega",
            example = "2024-03-25",
            type = "string",
            format = "date"
    )
    private LocalDate requiredDate;

    @Schema(
            description = "Fecha real de envío de la orden (null si no ha sido enviada)",
            example = "2024-03-18",
            type = "string",
            format = "date"
    )
    private LocalDate shippedDate;

    @Schema(
            description = "ID de la empresa transportista que maneja el envío",
            example = "3"
    )
    private Short shipperId;

    @Schema(
            description = "Costo de envío de la orden",
            example = "32.38"
    )
    private Float freight;

    @Schema(
            description = "Nombre del destinatario para el envío",
            example = "Vins et alcools Chevalier"
    )
    private String shipName;

    @Schema(
            description = "Dirección completa de envío",
            example = "59 rue de l'Abbaye"
    )
    private String shipAddress;

    @Schema(
            description = "Ciudad de destino del envío",
            example = "Reims"
    )
    private String shipCity;

    @Schema(
            description = "Región o estado de destino del envío",
            example = "Champagne-Ardenne"
    )
    private String shipRegion;

    @Schema(
            description = "Código postal de destino del envío",
            example = "51100"
    )
    private String shipPostalCode;

    @Schema(
            description = "País de destino del envío",
            example = "France"
    )
    private String shipCountry;

    @Schema(
            description = "Estado actual de la orden",
            example = "SHIPPED",
            allowableValues = {"PENDING", "SHIPPED", "DELIVERED", "CANCELLED"}
    )
    private String status;

    @Schema(
            description = "Lista detallada de productos incluidos en la orden con cantidades y precios"
    )
    private List<OrderDetailResponseDTO> details;
}
