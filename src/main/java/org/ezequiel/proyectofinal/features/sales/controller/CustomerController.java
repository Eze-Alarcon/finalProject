package org.ezequiel.proyectofinal.features.sales.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.ezequiel.proyectofinal.features.sales.dto.CustomerOrderHistoryDTO;
import org.ezequiel.proyectofinal.features.sales.dto.CustomerRequestDTO;
import org.ezequiel.proyectofinal.features.sales.dto.CustomerResponseDTO;
import org.ezequiel.proyectofinal.features.sales.service.CustomerService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
@Tag(name = "Sales - Customer", description = "API de gestión de clientes del sistema de ventas. Permite crear, consultar, actualizar y eliminar clientes, así como acceder a su historial de órdenes.")
public class CustomerController {

    private final CustomerService customerService;

    @Operation(
            summary = "Obtener todos los clientes",
            description = "Recupera la lista completa de clientes registrados en el sistema. Accesible para roles " +
                    "administrativos y operativos que requieren consultar información de clientes.",
            security = @SecurityRequirement(name = "bearer")
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200", 
                    description = "Lista de clientes recuperada exitosamente",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CustomerResponseDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "401", 
                    description = "Token de autenticación no válido o ausente",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "403", 
                    description = "Sin permisos suficientes - Se requiere rol ADMIN, WAREHOUSE, GESTOR o CONSULTA",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "500", 
                    description = "Error interno del servidor",
                    content = @Content(mediaType = "application/json")
            )
    })
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'WAREHOUSE', 'GESTOR', 'CONSULTA')")
    public ResponseEntity<List<CustomerResponseDTO>> findAll() {
        return ResponseEntity.ok(customerService.findAll());
    }

    @Operation(
            summary = "Obtener cliente por ID",
            description = "Recupera la información detallada de un cliente específico mediante su ID único. " +
                    "Accesible para roles con permisos de consulta y gestión.",
            security = @SecurityRequirement(name = "bearer")
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200", 
                    description = "Cliente encontrado y datos recuperados exitosamente",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CustomerResponseDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "401", 
                    description = "Token de autenticación no válido o ausente",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "403", 
                    description = "Sin permisos suficientes - Se requiere rol ADMIN, WAREHOUSE, GESTOR o CONSULTA",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "404", 
                    description = "Cliente no encontrado",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "500", 
                    description = "Error interno del servidor",
                    content = @Content(mediaType = "application/json")
            )
    })
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'WAREHOUSE', 'GESTOR', 'CONSULTA')")
    public ResponseEntity<CustomerResponseDTO> findById(
            @Parameter(
                    description = "ID único del cliente a consultar", 
                    required = true,
                    example = "ALFKI"
            )
            @PathVariable String id) {
        return ResponseEntity.ok(customerService.findById(id));
    }

    @Operation(
            summary = "Crear nuevo cliente",
            description = "Registra un nuevo cliente en el sistema de ventas. Solo usuarios con permisos " +
                    "administrativos (ADMIN) o de gestión (GESTOR) pueden crear clientes. El ID del cliente debe ser único.",
            security = @SecurityRequirement(name = "bearer")
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201", 
                    description = "Cliente creado exitosamente",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CustomerResponseDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400", 
                    description = "Datos de entrada inválidos o cliente ya existe",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "401", 
                    description = "Token de autenticación no válido o ausente",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "403", 
                    description = "Sin permisos suficientes - Se requiere rol ADMIN o GESTOR",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "500", 
                    description = "Error interno del servidor",
                    content = @Content(mediaType = "application/json")
            )
    })
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'GESTOR')")
    public ResponseEntity<CustomerResponseDTO> save(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos del nuevo cliente a registrar",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CustomerRequestDTO.class)
                    )
            )
            @Valid @RequestBody CustomerRequestDTO dto) {
        CustomerResponseDTO created = customerService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @Operation(
            summary = "Actualizar cliente",
            description = "Modifica los datos de un cliente existente. Soporta tanto actualización completa (PUT) " +
                    "como parcial (PATCH). Solo usuarios con permisos administrativos (ADMIN) o de gestión (GESTOR) " +
                    "pueden actualizar clientes.",
            security = @SecurityRequirement(name = "bearer")
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200", 
                    description = "Cliente actualizado exitosamente",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CustomerResponseDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400", 
                    description = "Datos de entrada inválidos",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "401", 
                    description = "Token de autenticación no válido o ausente",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "403", 
                    description = "Sin permisos suficientes - Se requiere rol ADMIN o GESTOR",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "404", 
                    description = "Cliente no encontrado",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "500", 
                    description = "Error interno del servidor",
                    content = @Content(mediaType = "application/json")
            )
    })
    @PatchMapping("/{id}")
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'GESTOR')")
    public ResponseEntity<CustomerResponseDTO> update(
            @Parameter(
                    description = "ID único del cliente a actualizar", 
                    required = true,
                    example = "ALFKI"
            )
            @PathVariable String id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos actualizados del cliente",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CustomerRequestDTO.class)
                    )
            )
            @RequestBody CustomerRequestDTO dto) {
        return ResponseEntity.ok(customerService.update(id, dto));
    }

    @Operation(
            summary = "Eliminar cliente",
            description = "Elimina permanentemente un cliente del sistema. Esta operación es irreversible y " +
                    "solo puede ser realizada por administradores. Se debe verificar que el cliente no tenga " +
                    "órdenes asociadas antes de eliminar.",
            security = @SecurityRequirement(name = "bearer")
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204", 
                    description = "Cliente eliminado exitosamente"
            ),
            @ApiResponse(
                    responseCode = "401", 
                    description = "Token de autenticación no válido o ausente",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "403", 
                    description = "Sin permisos suficientes - Se requiere rol ADMIN",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "404", 
                    description = "Cliente no encontrado",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "409", 
                    description = "Conflicto - No se puede eliminar cliente con órdenes asociadas",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "500", 
                    description = "Error interno del servidor",
                    content = @Content(mediaType = "application/json")
            )
    })
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<Void> delete(
            @Parameter(
                    description = "ID único del cliente a eliminar", 
                    required = true,
                    example = "ALFKI"
            )
            @PathVariable String id) {
        customerService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Obtener historial de órdenes del cliente",
            description = "Recupera el historial paginado de órdenes de un cliente específico. Incluye información " +
                    "como fecha de orden, estado actual y monto total. Los resultados se pueden filtrar y paginar " +
                    "usando los parámetros de paginación estándar de Spring Data.",
            security = @SecurityRequirement(name = "bearer")
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200", 
                    description = "Historial de órdenes recuperado exitosamente",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CustomerOrderHistoryDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "401", 
                    description = "Token de autenticación no válido o ausente",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "403", 
                    description = "Sin permisos suficientes - Se requiere rol ADMIN, WAREHOUSE, GESTOR o CONSULTA",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "404", 
                    description = "Cliente no encontrado",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "500", 
                    description = "Error interno del servidor",
                    content = @Content(mediaType = "application/json")
            )
    })
    @GetMapping("/{id}/orders")
    @PreAuthorize("hasAnyRole('ADMIN', 'WAREHOUSE', 'GESTOR', 'CONSULTA')")
    public ResponseEntity<Page<CustomerOrderHistoryDTO>> getOrderHistory(
            @Parameter(
                    description = "ID único del cliente cuyo historial se desea consultar", 
                    required = true,
                    example = "ALFKI"
            )
            @PathVariable String id,
            @Parameter(
                    description = "Parámetros de paginación (page, size, sort). Ejemplo: ?page=0&size=10&sort=orderDate,desc",
                    required = false
            )
            Pageable pageable) {
        return ResponseEntity.ok(customerService.getOrderHistory(id, pageable));
    }
}
