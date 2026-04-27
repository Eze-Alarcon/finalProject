package org.ezequiel.proyectofinal.features.sales.dto;

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
@Schema(description = "Datos requeridos para crear o actualizar un cliente en el sistema de ventas")
public class CustomerRequestDTO {

    @Schema(
            description = "Identificador único del cliente (código alfanumérico corto)",
            example = "ALFKI",
            maxLength = 5,
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotBlank(message = "Customer ID is required")
    @Size(max = 5, message = "Customer ID must not exceed 5 characters")
    private String customerId;

    @Schema(
            description = "Nombre oficial de la empresa o compañía del cliente",
            example = "Alfreds Futterkiste",
            maxLength = 40,
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotBlank(message = "Company name is required")
    @Size(max = 40, message = "Company name must not exceed 40 characters")
    private String companyName;

    @Schema(
            description = "Nombre completo de la persona de contacto en la empresa",
            example = "Maria Anders",
            maxLength = 30,
            requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    @Size(max = 30, message = "Contact name must not exceed 30 characters")
    private String contactName;

    @Schema(
            description = "Título o cargo de la persona de contacto",
            example = "Sales Representative",
            maxLength = 30,
            requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    @Size(max = 30, message = "Contact title must not exceed 30 characters")
    private String contactTitle;

    @Schema(
            description = "Dirección física completa del cliente",
            example = "Obere Str. 57",
            maxLength = 60,
            requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    @Size(max = 60, message = "Address must not exceed 60 characters")
    private String address;

    @Schema(
            description = "Ciudad donde se encuentra ubicado el cliente",
            example = "Berlin",
            maxLength = 15,
            requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    @Size(max = 15, message = "City must not exceed 15 characters")
    private String city;

    @Schema(
            description = "Estado, provincia o región del cliente",
            example = "Brandenburg",
            maxLength = 15,
            requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    @Size(max = 15, message = "Region must not exceed 15 characters")
    private String region;

    @Schema(
            description = "Código postal del cliente",
            example = "12209",
            maxLength = 10,
            requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    @Size(max = 10, message = "Postal code must not exceed 10 characters")
    private String postalCode;

    @Schema(
            description = "País donde reside el cliente",
            example = "Germany",
            maxLength = 15,
            requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    @Size(max = 15, message = "Country must not exceed 15 characters")
    private String country;

    @Schema(
            description = "Número de teléfono de contacto del cliente",
            example = "030-0074321",
            maxLength = 24,
            requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    @Size(max = 24, message = "Phone must not exceed 24 characters")
    private String phone;

    @Schema(
            description = "Número de fax del cliente (opcional)",
            example = "030-0076545",
            maxLength = 24,
            requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    @Size(max = 24, message = "Fax must not exceed 24 characters")
    private String fax;
}
