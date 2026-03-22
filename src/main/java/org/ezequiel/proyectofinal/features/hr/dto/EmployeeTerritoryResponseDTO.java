package org.ezequiel.proyectofinal.features.hr.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeTerritoryResponseDTO {

    private Short employeeId;
    private String employeeFullName;
    private String territoryId;
    private String territoryDescription;
}
