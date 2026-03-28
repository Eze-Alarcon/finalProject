package org.ezequiel.proyectofinal.features.catalog.controller;

import org.ezequiel.proyectofinal.core.exceptions.GlobalExceptionHandler;
import org.ezequiel.proyectofinal.core.security.JwtService;
import org.ezequiel.proyectofinal.features.catalog.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
@Import(GlobalExceptionHandler.class)
@AutoConfigureMockMvc(addFilters = false)
class AccessDeniedTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProductService productService;

    @MockitoBean
    private JwtService jwtService;

    @Test
    @WithMockUser
    void givenUserNoPermission_whenAccessResource_thenReturn403Forbidden() throws Exception {
        // Given
        when(productService.findAll()).thenThrow(new AccessDeniedException("Access Denied"));

        // When & Then
        mockMvc.perform(get("/api/v1/products")
                        .with(csrf()))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.title").value("Forbidden"))
                .andExpect(jsonPath("$.detail").value("You do not have permission to access this resource"))
                .andExpect(jsonPath("$.status").value(403));
    }
}
