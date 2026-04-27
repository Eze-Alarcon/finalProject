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
@Schema(description = "Información completa de una empresa transportista del sistema de ventas")
public class ShipperResponseDTO {

    @Schema(
            description = "Identificador único del transportista en el sistema",
            example = "1"
    )
    private Short shipperId;
    
    @Schema(
            description = "Nombre oficial de la empresa transportista",
            example = "Speedy Express"
    )
    private String companyName;
    
    @Schema(
            description = "Número de teléfono de contacto de la empresa",
            example = "(503) 555-9831"
    )
    private String phone;
}
