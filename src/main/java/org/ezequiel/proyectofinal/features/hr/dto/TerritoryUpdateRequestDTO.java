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
public class TerritoryUpdateRequestDTO {
    @NotBlank(message = "Territory description is required")
    @Size(max = 60, message = "Territory description must not exceed 60 characters")
    private String territoryDescription;

    @NotNull(message = "Region ID is required")
    private Short regionId;
}
