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
@Schema(name = "TerritoryResponseDTO", description = "API response with complete sales territory information including region details")
public class TerritoryResponseDTO {

    @Schema(
            name = "territoryId",
            description = "Unique territory identifier code.",
            example = "01001",
            accessMode = Schema.AccessMode.READ_ONLY
    )
    private String territoryId;

    @Schema(
            name = "territoryDescription",
            description = "Territory description or name representing a geographic or business area for sales operations.",
            example = "Northeast"
    )
    private String territoryDescription;

    @Schema(
            name = "regionId",
            description = "Identifier of the geographic region this territory belongs to.",
            example = "1"
    )
    private Short regionId;

    @Schema(
            name = "regionDescription",
            description = "Name of the geographic region this territory belongs to. Provides context about the larger regional structure.",
            example = "North"
    )
    private String regionDescription;
}
