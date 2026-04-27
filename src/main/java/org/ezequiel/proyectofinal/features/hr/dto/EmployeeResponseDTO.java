package org.ezequiel.proyectofinal.features.hr.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Información completa de un empleado del sistema")
public class EmployeeResponseDTO {

    @Schema(
        description = "Identificador único del empleado en el sistema",
        example = "5"
    )
    private Short employeeId;

    @Schema(
        description = "Apellidos del empleado",
        example = "Davolio"
    )
    private String lastName;

    @Schema(
        description = "Nombre del empleado",
        example = "Nancy"
    )
    private String firstName;

    @Schema(
        description = "Puesto o cargo del empleado dentro de la organización",
        example = "Sales Representative"
    )
    private String title;

    @Schema(
        description = "Tratamiento o título de cortesía",
        example = "Ms."
    )
    private String titleOfCourtesy;

    @Schema(
        description = "Fecha de nacimiento del empleado",
        example = "1968-05-11",
        type = "string",
        format = "date"
    )
    private LocalDate birthDate;

    @Schema(
        description = "Fecha de contratación del empleado",
        example = "2010-08-15",
        type = "string",
        format = "date"
    )
    private LocalDate hireDate;

    @Schema(
        description = "Dirección de residencia o contacto del empleado",
        example = "507 - 20th Ave. E. Apt. 2A"
    )
    private String address;

    @Schema(
        description = "Ciudad de residencia del empleado",
        example = "Seattle"
    )
    private String city;

    @Schema(
        description = "Región, estado o provincia del empleado",
        example = "WA"
    )
    private String region;

    @Schema(
        description = "Código postal de residencia del empleado",
        example = "98122"
    )
    private String postalCode;

    @Schema(
        description = "País de residencia del empleado",
        example = "USA"
    )
    private String country;

    @Schema(
        description = "Número de teléfono de contacto del empleado",
        example = "(206) 555-9857"
    )
    private String homePhone;

    @Schema(
        description = "Extensión telefónica interna",
        example = "543"
    )
    private String extension;

    @Schema(
        description = "Notas internas sobre el empleado",
        example = "Works with the marketing team."
    )
    private String notes;

    @Schema(
        description = "ID del empleado al que reporta jerárquicamente",
        example = "2"
    )
    private Short reportsToId;

    @Schema(
        description = "Nombre completo del supervisor del empleado",
        example = "Andrew Fuller"
    )
    private String reportsToFullName;

    @Schema(
        description = "Ruta o referencia de la foto del empleado",
        example = "/images/employees/nancy-davolio.jpg"
    )
    private String photoPath;
}
