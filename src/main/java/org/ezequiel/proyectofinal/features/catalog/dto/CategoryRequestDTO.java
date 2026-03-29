package org.ezequiel.proyectofinal.features.catalog.dto;

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
public class CategoryRequestDTO {


    @NotBlank(message = "Category name is required")
    @Size(max = 15, message = "Category name must not exceed 15 characters")
    private String categoryName;

    private String description;

    private byte[] picture;
}
