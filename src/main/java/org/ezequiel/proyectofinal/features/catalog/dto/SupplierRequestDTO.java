package org.ezequiel.proyectofinal.features.catalog.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SupplierRequestDTO {


    @NotBlank(message = "Company name is required")
    @Size(max = 40, message = "Company name must not exceed 40 characters")
    private String companyName;

    @Size(max = 30, message = "Contact name must not exceed 30 characters")
    private String contactName;

    @Size(max = 30, message = "Contact title must not exceed 30 characters")
    private String contactTitle;

    @Size(max = 60, message = "Address must not exceed 60 characters")
    private String address;

    @Size(max = 15, message = "City must not exceed 15 characters")
    private String city;

    @Size(max = 15, message = "Region must not exceed 15 characters")
    private String region;

    @Size(max = 10, message = "Postal code must not exceed 10 characters")
    private String postalCode;

    @Size(max = 15, message = "Country must not exceed 15 characters")
    private String country;

    @Size(max = 24, message = "Phone must not exceed 24 characters")
    private String phone;

    @Size(max = 24, message = "Fax must not exceed 24 characters")
    private String fax;

    private String homepage;
}
