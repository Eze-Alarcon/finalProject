package org.ezequiel.proyectofinal.features.hr.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsStateResponseDTO {

    private Short stateId;
    private String stateName;
    private String stateAbbr;
    private String stateRegion;
}
