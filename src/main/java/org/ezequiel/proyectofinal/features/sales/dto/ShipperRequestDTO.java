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
@Schema(description = "Datos requeridos para crear o actualizar una empresa transportista en el sistema de ventas")
public class ShipperRequestDTO {

    @Schema(
            description = "Nombre oficial de la empresa transportista que realizará los envíos",
            example = "Speedy Express",
            maxLength = 40,
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotBlank(message = "Company name is required")
    @Size(max = 40, message = "Company name must not exceed 40 characters")
    private String companyName;

    @Schema(
            description = "Número de teléfono de contacto de la empresa transportista",
            example = "(503) 555-9831",
            maxLength = 24,
            requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    @Size(max = 24, message = "Phone must not exceed 24 characters")
    private String phone;
}
