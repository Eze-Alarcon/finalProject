package org.ezequiel.proyectofinal.features.hr.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Datos requeridos para crear o actualizar un empleado en el sistema")
public class EmployeeRequestDTO {


        @Schema(
            description = "Apellidos del empleado",
            example = "Davolio",
            maxLength = 20,
            requiredMode = Schema.RequiredMode.REQUIRED
        )
        @NotBlank(message = "Last name is required")
        @Size(max = 20, message = "Last name must not exceed 20 characters")
        private String lastName;

        @Schema(
            description = "Nombre del empleado",
            example = "Nancy",
            maxLength = 10,
            requiredMode = Schema.RequiredMode.REQUIRED
        )
        @NotBlank(message = "First name is required")
        @Size(max = 10, message = "First name must not exceed 10 characters")
        private String firstName;

        @Schema(
            description = "Puesto o cargo del empleado dentro de la organización",
            example = "Sales Representative",
            maxLength = 30,
            requiredMode = Schema.RequiredMode.NOT_REQUIRED
        )
        @Size(max = 30, message = "Title must not exceed 30 characters")
        private String title;

        @Schema(
            description = "Tratamiento o título de cortesía",
            example = "Ms.",
            maxLength = 25,
            requiredMode = Schema.RequiredMode.NOT_REQUIRED
        )
        @Size(max = 25, message = "Title of courtesy must not exceed 25 characters")
        private String titleOfCourtesy;

        @Schema(
            description = "Fecha de nacimiento del empleado",
            example = "1968-05-11",
            type = "string",
            format = "date",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED
        )
        private LocalDate birthDate;

        @Schema(
            description = "Fecha de contratación del empleado",
            example = "2010-08-15",
            type = "string",
            format = "date",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED
        )
        private LocalDate hireDate;

        @Schema(
            description = "Dirección de residencia o contacto del empleado",
            example = "507 - 20th Ave. E. Apt. 2A",
            maxLength = 60,
            requiredMode = Schema.RequiredMode.NOT_REQUIRED
        )
        @Size(max = 60, message = "Address must not exceed 60 characters")
        private String address;

        @Schema(
            description = "Ciudad de residencia del empleado",
            example = "Seattle",
            maxLength = 15,
            requiredMode = Schema.RequiredMode.NOT_REQUIRED
        )
        @Size(max = 15, message = "City must not exceed 15 characters")
        private String city;

        @Schema(
            description = "Región, estado o provincia del empleado",
            example = "WA",
            maxLength = 15,
            requiredMode = Schema.RequiredMode.NOT_REQUIRED
        )
        @Size(max = 15, message = "Region must not exceed 15 characters")
        private String region;

        @Schema(
            description = "Código postal de residencia del empleado",
            example = "98122",
            maxLength = 10,
            requiredMode = Schema.RequiredMode.NOT_REQUIRED
        )
        @Size(max = 10, message = "Postal code must not exceed 10 characters")
        private String postalCode;

        @Schema(
            description = "País de residencia del empleado",
            example = "USA",
            maxLength = 15,
            requiredMode = Schema.RequiredMode.NOT_REQUIRED
        )
        @Size(max = 15, message = "Country must not exceed 15 characters")
        private String country;

        @Schema(
            description = "Número de teléfono de contacto del empleado",
            example = "(206) 555-9857",
            maxLength = 24,
            requiredMode = Schema.RequiredMode.NOT_REQUIRED
        )
        @Size(max = 24, message = "Home phone must not exceed 24 characters")
        private String homePhone;

        @Schema(
            description = "Extensión telefónica interna",
            example = "543",
            maxLength = 4,
            requiredMode = Schema.RequiredMode.NOT_REQUIRED
        )
        @Size(max = 4, message = "Extension must not exceed 4 characters")
        private String extension;

        @Schema(
            description = "Notas internas sobre el empleado",
            example = "Works with the marketing team.",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED
        )
        private String notes;

        @Schema(
            description = "ID del empleado al que reporta jerárquicamente. Permite representar la relación supervisor-subordinado.",
            example = "2",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED
        )
        private Short reportsToId;

        @Schema(
            description = "Ruta o referencia de la foto del empleado",
            example = "/images/employees/nancy-davolio.jpg",
            maxLength = 255,
            requiredMode = Schema.RequiredMode.NOT_REQUIRED
        )
        @Size(max = 255, message = "Photo path must not exceed 255 characters")
        private String photoPath;
}
