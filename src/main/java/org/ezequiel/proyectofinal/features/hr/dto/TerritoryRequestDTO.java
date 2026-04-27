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
@Schema(name = "TerritoryRequestDTO", description = "Request payload for creating a new sales territory")
public class TerritoryRequestDTO {

    @Schema(
            name = "territoryId",
            description = "Unique territory identifier code. Must be alphanumeric and not exceed 20 characters. Examples: '01001', '01002', 'NE-001', 'WEST-02'",
            example = "01001",
            minLength = 1,
            maxLength = 20,
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotBlank(message = "Territory ID is required")
    @Size(max = 20, message = "Territory ID must not exceed 20 characters")
    private String territoryId;

    @Schema(
            name = "territoryDescription",
            description = "Territory description or name. Must be unique, non-empty, and not exceed 60 characters. Examples: 'Northeast Region', 'Pacific Northwest', 'New England'",
            example = "Northeast",
            minLength = 1,
            maxLength = 60,
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotBlank(message = "Territory description is required")
    @Size(max = 60, message = "Territory description must not exceed 60 characters")
    private String territoryDescription;

    @Schema(
            name = "regionId",
            description = "Identifier of the geographic region this territory belongs to. Must reference an existing Region entity.",
            example = "1",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotNull(message = "Region ID is required")
    private Short regionId;
}
