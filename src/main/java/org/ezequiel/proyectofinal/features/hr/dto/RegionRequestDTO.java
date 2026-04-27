package org.ezequiel.proyectofinal.features.hr.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "RegionRequestDTO", description = "Request payload for creating or updating a geographic region")
public class RegionRequestDTO {

    @Schema(
            name = "regionDescription",
            description = "Geographic region description. Must be unique, non-empty, and not exceed 60 characters. Examples: 'North', 'South', 'East', 'West', 'Northeast', 'Pacific'",
            example = "North",
            minLength = 1,
            maxLength = 60,
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotBlank(message = "Region description is required")
    @Size(max = 60, message = "Region description must not exceed 60 characters")
    private String regionDescription;
}
