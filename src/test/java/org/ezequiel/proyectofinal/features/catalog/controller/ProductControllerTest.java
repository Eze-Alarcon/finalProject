package org.ezequiel.proyectofinal.features.catalog.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.ezequiel.proyectofinal.core.security.JwtService;
import org.ezequiel.proyectofinal.features.catalog.dto.ProductRequestDTO;
import org.ezequiel.proyectofinal.features.catalog.dto.ProductResponseDTO;
import org.ezequiel.proyectofinal.features.catalog.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private ProductService productService;

    @MockitoBean
    private JwtService jwtService;

    @Test
    @WithMockUser(roles = "ADMIN")
    void givenValidProduct_whenSave_thenReturn201Created() throws Exception {
        // Given
        ProductRequestDTO requestDTO = new ProductRequestDTO();
        requestDTO.setProductName("Test Product");
        requestDTO.setDiscontinued(0);
        // categoryId y supplierId opcionales según el DTO

        ProductResponseDTO responseDTO = new ProductResponseDTO();
        responseDTO.setProductId((short) 1);
        responseDTO.setProductName("Test Product");

        when(productService.save(any(ProductRequestDTO.class))).thenReturn(responseDTO);

        // When & Then
        mockMvc.perform(post("/api/v1/products")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.productId").value(1))
                .andExpect(jsonPath("$.productName").value("Test Product"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void givenInvalidProduct_whenSave_thenReturn400BadRequest() throws Exception {
        // Given
        // El DTO real tiene @NotNull en productId, @NotBlank en productName y @NotNull en discontinued
        ProductRequestDTO invalidDTO = new ProductRequestDTO();
        invalidDTO.setProductName(""); // Violates @NotBlank

        // When & Then
        mockMvc.perform(post("/api/v1/products")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void givenUnauthenticatedUser_whenFindAll_thenReturnUnauthorized() throws Exception {
        // When & Then
        mockMvc.perform(get("/api/v1/products"))
                .andExpect(status().isUnauthorized());
    }
}
