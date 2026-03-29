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
public class RegionRequestDTO {


    @NotBlank(message = "Region description is required")
    @Size(max = 60, message = "Region description must not exceed 60 characters")
    private String regionDescription;
}
