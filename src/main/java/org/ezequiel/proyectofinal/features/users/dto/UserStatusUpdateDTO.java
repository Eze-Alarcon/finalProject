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
@Schema(description = "Datos requeridos para actualizar el estado de un usuario")
public class UserStatusUpdateDTO {
    
    @Schema(
            description = "Estado del usuario - true para habilitar, false para deshabilitar. " +
                    "Un usuario deshabilitado no podrá acceder al sistema",
            example = "true",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private Boolean enabled;
}
