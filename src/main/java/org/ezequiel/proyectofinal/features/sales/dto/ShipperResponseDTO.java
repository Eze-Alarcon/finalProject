package org.ezequiel.proyectofinal.features.sales.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ShipperResponseDTO {

    private Short shipperId;
    private String companyName;
    private String phone;
}
