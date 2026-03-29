package org.ezequiel.proyectofinal.features.sales.dto;

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
public class ShipperRequestDTO {


    @NotBlank(message = "Company name is required")
    @Size(max = 40, message = "Company name must not exceed 40 characters")
    private String companyName;

    @Size(max = 24, message = "Phone must not exceed 24 characters")
    private String phone;
}
