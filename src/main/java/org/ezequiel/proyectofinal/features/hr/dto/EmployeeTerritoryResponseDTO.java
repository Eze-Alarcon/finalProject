package org.ezequiel.proyectofinal.features.hr.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Información de la relación entre un empleado y sus territorios asignados")
public class EmployeeTerritoryResponseDTO {

    @Schema(
        description = "ID único del empleado asociado a la relación",
        example = "5"
    )
    private Short employeeId;

    @Schema(
        description = "Nombre completo del empleado asociado",
        example = "Nancy Davolio"
    )
    private String employeeFullName;

    @Schema(
        description = "Territorio asociado al empleado",
        example = "01581"
    )
    private String territories;
}
