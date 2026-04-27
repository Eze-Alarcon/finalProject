package org.ezequiel.proyectofinal.features.users.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Información completa de un usuario del sistema")
public class UserResponseDTO {
    
    @Schema(
            description = "Identificador único del usuario en el sistema",
            example = "1"
    )
    private Integer userId;
    
    @Schema(
            description = "Nombre de usuario utilizado para el acceso al sistema",
            example = "jsmith"
    )
    private String username;
    
    @Schema(
            description = "Rol actual del usuario que determina sus permisos",
            example = "USER",
            allowableValues = {"ADMIN", "USER", "MANAGER"}
    )
    private String role;
    
    @Schema(
            description = "Estado del usuario - indica si puede acceder al sistema",
            example = "true"
    )
    private Boolean enabled;
    
    @Schema(
            description = "ID del empleado asociado (si aplica)",
            example = "123"
    )
    private Short employeeId;
}
