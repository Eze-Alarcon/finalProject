package org.ezequiel.proyectofinal.features.catalog.dto;

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
@Schema(name = "SupplierRequestDTO", description = "Datos requeridos para crear o actualizar un proveedor del catálogo")
public class SupplierRequestDTO {


    @NotBlank(message = "Company name is required")
    @Size(max = 40, message = "Company name must not exceed 40 characters")
        @Schema(
            description = "Nombre de la empresa proveedora",
            example = "Exotic Liquids",
            maxLength = 40,
            requiredMode = Schema.RequiredMode.REQUIRED
        )
    private String companyName;

    @Size(max = 30, message = "Contact name must not exceed 30 characters")
        @Schema(
            description = "Nombre de la persona de contacto en la empresa proveedora",
            example = "Charlotte Cooper",
            maxLength = 30,
            nullable = true
        )
    private String contactName;

    @Size(max = 30, message = "Contact title must not exceed 30 characters")
        @Schema(
            description = "Cargo o título de la persona de contacto",
            example = "Purchasing Manager",
            maxLength = 30,
            nullable = true
        )
    private String contactTitle;

    @Size(max = 60, message = "Address must not exceed 60 characters")
        @Schema(
            description = "Dirección postal del proveedor",
            example = "49 Gilbert St.",
            maxLength = 60,
            nullable = true
        )
    private String address;

    @Size(max = 15, message = "City must not exceed 15 characters")
        @Schema(
            description = "Ciudad del proveedor",
            example = "London",
            maxLength = 15,
            nullable = true
        )
    private String city;

    @Size(max = 15, message = "Region must not exceed 15 characters")
        @Schema(
            description = "Región, provincia o área administrativa",
            example = "Greater London",
            maxLength = 15,
            nullable = true
        )
    private String region;

    @Size(max = 10, message = "Postal code must not exceed 10 characters")
        @Schema(
            description = "Código postal",
            example = "EC1 4SD",
            maxLength = 10,
            nullable = true
        )
    private String postalCode;

    @Size(max = 15, message = "Country must not exceed 15 characters")
        @Schema(
            description = "País del proveedor",
            example = "UK",
            maxLength = 15,
            nullable = true
        )
    private String country;

    @Size(max = 24, message = "Phone must not exceed 24 characters")
        @Schema(
            description = "Número de teléfono de contacto",
            example = "(171) 555-2222",
            maxLength = 24,
            nullable = true
        )
    private String phone;

    @Size(max = 24, message = "Fax must not exceed 24 characters")
        @Schema(
            description = "Número de fax, si aplica",
            example = "(171) 555-2223",
            maxLength = 24,
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
