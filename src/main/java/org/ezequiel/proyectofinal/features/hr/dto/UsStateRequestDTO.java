package org.ezequiel.proyectofinal.features.hr.dto;

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
public class UsStateRequestDTO {


    @Size(max = 100, message = "State name must not exceed 100 characters")
    private String stateName;

    @Size(max = 2, message = "State abbreviation must not exceed 2 characters")
    private String stateAbbr;

    @Size(max = 50, message = "State region must not exceed 50 characters")
    private String stateRegion;
}
