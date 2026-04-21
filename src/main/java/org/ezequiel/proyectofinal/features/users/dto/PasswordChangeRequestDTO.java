package org.ezequiel.proyectofinal.features.users.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswordChangeRequestDTO {
    private String oldPassword;
    
    @NotBlank(message = "New password is required")
    @Size(min = 4, max = 255, message = "Password must be between 4 and 255 characters")
    private String newPassword;
}
