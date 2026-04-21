package org.ezequiel.proyectofinal.features.hr.dto;

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
public class EmployeeTerritoryRequestDTO {

    @NotNull(message = "Employee ID is required")
    private Short employeeId;

    @NotBlank(message = "Territory ID is required")
    @Size(max = 20, message = "Territory ID must not exceed 20 characters")
    private String territoryId;
}
