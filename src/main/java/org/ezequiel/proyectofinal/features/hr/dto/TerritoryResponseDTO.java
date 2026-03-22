package org.ezequiel.proyectofinal.features.hr.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TerritoryResponseDTO {

    private String territoryId;
    private String territoryDescription;
    private Short regionId;
    private String regionDescription;
}
