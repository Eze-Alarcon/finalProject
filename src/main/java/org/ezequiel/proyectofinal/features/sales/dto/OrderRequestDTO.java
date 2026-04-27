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
@Schema(description = "Datos requeridos para crear o actualizar una orden de venta en el sistema")
public class OrderRequestDTO {

    @Schema(
            description = "ID del cliente que realiza la orden (debe existir en el sistema)",
            example = "ALFKI",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String customerId;

    @Schema(
            description = "ID del empleado asignado para gestionar la orden",
            example = "5",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private Short employeeId;

    @Schema(
            description = "Fecha en que se realiza la orden",
            example = "2024-03-15",
            type = "string",
            format = "date",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    private LocalDate orderDate;

    @Schema(
            description = "Fecha límite requerida por el cliente para la entrega",
            example = "2024-03-25",
            type = "string",
            format = "date",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    private LocalDate requiredDate;

    @Schema(
            description = "ID de la empresa transportista que manejará el envío",
            example = "3",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    private Short shipperId;

    @Schema(
            description = "Costo de envío de la orden",
            example = "32.38",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    private Float freight;

    @Schema(
            description = "Nombre del destinatario para el envío",
            example = "Vins et alcools Chevalier",
            maxLength = 40,
            requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    private String shipName;

    @Schema(
            description = "Dirección de envío completa",
            example = "59 rue de l'Abbaye",
            maxLength = 60,
            requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    private String shipAddress;

    @Schema(
            description = "Ciudad de destino del envío",
            example = "Reims",
            maxLength = 15,
            requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    private String shipCity;

    @Schema(
            description = "Región o estado de destino del envío",
            example = "Champagne-Ardenne",
            maxLength = 15,
            requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    private String shipRegion;

    @Schema(
            description = "Código postal de destino del envío",
            example = "51100",
            maxLength = 10,
            requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    private String shipPostalCode;

    @Schema(
            description = "País de destino del envío",
            example = "France",
            maxLength = 15,
            requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    private String shipCountry;

    @Schema(
            description = "Lista de productos y cantidades incluidos en la orden",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private List<OrderDetailRequestDTO> details;
}
