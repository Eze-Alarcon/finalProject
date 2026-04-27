package org.ezequiel.proyectofinal.features.hr.dto;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Datos requeridos para asignar un territorio a un empleado")
public class EmployeeTerritoryRequestDTO {

    @Schema(
            description = "ID único del empleado al que se asignará el territorio",
            example = "5",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotNull(message = "Employee ID is required")
    private Short employeeId;

    @Schema(
            description = "ID del territorio que se asignará al empleado",
            example = "01581",
            maxLength = 20,
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotBlank(message = "Territory ID is required")
    @Size(max = 20, message = "Territory ID must not exceed 20 characters")
    private String territoryId;
}
