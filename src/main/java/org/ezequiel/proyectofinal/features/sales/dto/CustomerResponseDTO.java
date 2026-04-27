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
@Schema(description = "Información completa de un cliente del sistema de ventas")
public class CustomerResponseDTO {

    @Schema(
            description = "Identificador único del cliente",
            example = "ALFKI"
    )
    private String customerId;

    @Schema(
            description = "Nombre oficial de la empresa o compañía",
            example = "Alfreds Futterkiste"
    )
    private String companyName;

    @Schema(
            description = "Nombre completo de la persona de contacto",
            example = "Maria Anders"
    )
    private String contactName;

    @Schema(
            description = "Título o cargo de la persona de contacto",
            example = "Sales Representative"
    )
    private String contactTitle;

    @Schema(
            description = "Dirección física completa",
            example = "Obere Str. 57"
    )
    private String address;

    @Schema(
            description = "Ciudad de ubicación",
            example = "Berlin"
    )
    private String city;

    @Schema(
            description = "Estado, provincia o región",
            example = "Brandenburg"
    )
    private String region;

    @Schema(
            description = "Código postal",
            example = "12209"
    )
    private String postalCode;

    @Schema(
            description = "País de residencia",
            example = "Germany"
    )
    private String country;

    @Schema(
            description = "Número de teléfono de contacto",
            example = "030-0074321"
    )
    private String phone;

    @Schema(
            description = "Número de fax",
            example = "030-0076545"
    )
    private String fax;
}
