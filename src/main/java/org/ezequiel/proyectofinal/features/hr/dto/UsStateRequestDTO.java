package org.ezequiel.proyectofinal.features.hr.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "UsStateRequestDTO", description = "Request payload for creating or updating a US state reference")
public class UsStateRequestDTO {

    @Schema(
            name = "stateName",
            description = "Full name of the US state. Must not exceed 100 characters. Examples: 'California', 'Texas', 'New York', 'Florida'",
            example = "California",
            maxLength = 100
    )
    @Size(max = 100, message = "State name must not exceed 100 characters")
    private String stateName;

    @Schema(
            name = "stateAbbr",
            description = "Two-letter abbreviation of the US state. Examples: 'CA', 'TX', 'NY', 'FL'",
            example = "CA",
            maxLength = 2
    )
    @Size(max = 2, message = "State abbreviation must not exceed 2 characters")
    private String stateAbbr;

    @Schema(
            name = "stateRegion",
            description = "Geographic or business region classification of the state. Must not exceed 50 characters. Examples: 'West', 'South', 'Northeast', 'Midwest'",
            example = "West",
            maxLength = 50
    )
    @Size(max = 50, message = "State region must not exceed 50 characters")
    private String stateRegion;
}
