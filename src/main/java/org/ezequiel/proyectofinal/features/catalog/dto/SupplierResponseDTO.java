package org.ezequiel.proyectofinal.features.catalog.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "SupplierResponseDTO", description = "Información completa de un proveedor del catálogo")
public class SupplierResponseDTO {

    @Schema(
        description = "Identificador único del proveedor",
        example = "3"
    )
    private Short supplierId;

    @Schema(
        description = "Nombre de la empresa proveedora",
        example = "Exotic Liquids"
    )
    private String companyName;

    @Schema(
        description = "Nombre de la persona de contacto en la empresa proveedora",
        example = "Charlotte Cooper",
        nullable = true
    )
    private String contactName;

    @Schema(
        description = "Cargo o título de la persona de contacto",
        example = "Purchasing Manager",
        nullable = true
    )
    private String contactTitle;

    @Schema(
        description = "Dirección postal del proveedor",
        example = "49 Gilbert St.",
        nullable = true
    )
    private String address;

    @Schema(
        description = "Ciudad del proveedor",
        example = "London",
        nullable = true
    )
    private String city;

    @Schema(
        description = "Región, provincia o área administrativa",
        example = "Greater London",
        nullable = true
    )
    private String region;

    @Schema(
        description = "Código postal del proveedor",
        example = "EC1 4SD",
        nullable = true
    )
    private String postalCode;

    @Schema(
        description = "País del proveedor",
        example = "UK",
        nullable = true
    )
    private String country;

    @Schema(
        description = "Número de teléfono de contacto",
        example = "(171) 555-2222",
        nullable = true
    )
    private String phone;

    @Schema(
        description = "Número de fax, si aplica",
        example = "(171) 555-2223",
        nullable = true
    )
    private String fax;

    @Schema(
        description = "Sitio web del proveedor",
        example = "http://exoticliquids.example.com",
        nullable = true
    )
    private String homepage;
}
