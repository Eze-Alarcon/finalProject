package org.ezequiel.proyectofinal.features.hr.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class EmployeeRequestDTO {


    @NotBlank(message = "Last name is required")
    @Size(max = 20, message = "Last name must not exceed 20 characters")
    private String lastName;

    @NotBlank(message = "First name is required")
    @Size(max = 10, message = "First name must not exceed 10 characters")
    private String firstName;

    @Size(max = 30, message = "Title must not exceed 30 characters")
    private String title;

    @Size(max = 25, message = "Title of courtesy must not exceed 25 characters")
    private String titleOfCourtesy;

    private LocalDate birthDate;

    private LocalDate hireDate;

    @Size(max = 60, message = "Address must not exceed 60 characters")
    private String address;

    @Size(max = 15, message = "City must not exceed 15 characters")
    private String city;

    @Size(max = 15, message = "Region must not exceed 15 characters")
    private String region;

    @Size(max = 10, message = "Postal code must not exceed 10 characters")
    private String postalCode;

    @Size(max = 15, message = "Country must not exceed 15 characters")
    private String country;

    @Size(max = 24, message = "Home phone must not exceed 24 characters")
    private String homePhone;

    @Size(max = 4, message = "Extension must not exceed 4 characters")
    private String extension;

    private String notes;

    private Short reportsToId;

    @Size(max = 255, message = "Photo path must not exceed 255 characters")
    private String photoPath;

    @Size(max = 50, message = "Username must not exceed 50 characters")
    private String username;

    @Size(max = 50, message = "Role must not exceed 50 characters")
    private String role;

    private Boolean activo;
}
