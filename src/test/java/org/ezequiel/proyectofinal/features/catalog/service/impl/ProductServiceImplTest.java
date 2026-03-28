package org.ezequiel.proyectofinal.features.catalog.service.impl;

import org.ezequiel.proyectofinal.core.exceptions.ResourceNotFoundException;
import org.ezequiel.proyectofinal.features.catalog.dto.ProductRequestDTO;
import org.ezequiel.proyectofinal.features.catalog.dto.ProductResponseDTO;
import org.ezequiel.proyectofinal.features.catalog.entity.Category;
import org.ezequiel.proyectofinal.features.catalog.entity.Product;
import org.ezequiel.proyectofinal.features.catalog.entity.Supplier;
import org.ezequiel.proyectofinal.features.catalog.mapper.ProductMapper;
import org.ezequiel.proyectofinal.features.catalog.repository.CategoryRepository;
import org.ezequiel.proyectofinal.features.catalog.repository.ProductRepository;
import org.ezequiel.proyectofinal.features.catalog.repository.SupplierRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private SupplierRepository supplierRepository;

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private ProductServiceImpl productService;

    private Product product;
    private ProductRequestDTO productRequestDTO;
    private ProductResponseDTO productResponseDTO;
    private Category category;
    private Supplier supplier;

    @BeforeEach
    void setUp() {
        category = new Category();
        category.setCategoryId((short) 1);

        supplier = new Supplier();
        supplier.setSupplierId((short) 1);

        product = new Product();
        product.setProductId((short) 1);

        productRequestDTO = new ProductRequestDTO();
        productRequestDTO.setCategoryId((short) 1);
        productRequestDTO.setSupplierId((short) 1);

        productResponseDTO = new ProductResponseDTO();
        productResponseDTO.setProductId((short) 1);
    }

    @Test
    void givenProducts_whenFindAll_thenReturnsMappedList() {
        List<Product> products = List.of(product);

        when(productRepository.findAll()).thenReturn(products);
        when(productMapper.toResponseDTO(product)).thenReturn(productResponseDTO);

        List<ProductResponseDTO> result = productService.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());

        verify(productRepository).findAll();
        verify(productMapper).toResponseDTO(product);
        verifyNoMoreInteractions(productRepository, productMapper);
    }

    @Test
    void givenExistingId_whenFindById_thenReturnsProduct() {
        when(productRepository.findById((short) 1)).thenReturn(Optional.of(product));
        when(productMapper.toResponseDTO(product)).thenReturn(productResponseDTO);

        ProductResponseDTO result = productService.findById((short) 1);

        assertNotNull(result);

        verify(productRepository).findById((short) 1);
        verify(productMapper).toResponseDTO(product);
        verifyNoMoreInteractions(productRepository, productMapper);
    }

    @Test
    void givenNonExistingId_whenFindById_thenThrowsException() {
        when(productRepository.findById((short) 1)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> productService.findById((short) 1));

        verify(productRepository).findById((short) 1);
        verifyNoInteractions(productMapper);
    }

    @Test
    void givenValidRequest_whenSave_thenPersistProductWithRelations() {
        when(productMapper.toEntity(productRequestDTO)).thenReturn(product);
        when(categoryRepository.findById((short) 1)).thenReturn(Optional.of(category));
        when(supplierRepository.findById((short) 1)).thenReturn(Optional.of(supplier));
        when(productRepository.save(product)).thenReturn(product);
        when(productMapper.toResponseDTO(product)).thenReturn(productResponseDTO);

        ProductResponseDTO result = productService.save(productRequestDTO);

        assertNotNull(result);
        assertEquals(category, product.getCategory());
        assertEquals(supplier, product.getSupplier());

        verify(productMapper).toEntity(productRequestDTO);
        verify(categoryRepository).findById((short) 1);
        verify(supplierRepository).findById((short) 1);
        verify(productRepository).save(product);
        verify(productMapper).toResponseDTO(product);
    }

    @Test
    void givenNullCategoryAndSupplier_whenSave_thenSetNullRelations() {
        productRequestDTO.setCategoryId(null);
        productRequestDTO.setSupplierId(null);

        when(productMapper.toEntity(productRequestDTO)).thenReturn(product);
        when(productRepository.save(product)).thenReturn(product);
        when(productMapper.toResponseDTO(product)).thenReturn(productResponseDTO);

        productService.save(productRequestDTO);

        assertNull(product.getCategory());
        assertNull(product.getSupplier());

        verify(categoryRepository, never()).findById(any());
        verify(supplierRepository, never()).findById(any());
    }

    @Test
    void givenNonExistingCategory_whenSave_thenThrowsException() {
        when(productMapper.toEntity(productRequestDTO)).thenReturn(product);
        when(categoryRepository.findById((short) 1)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> productService.save(productRequestDTO));

        verify(categoryRepository).findById((short) 1);
        verify(productRepository, never()).save(any());
    }

    @Test
    void givenExistingProduct_whenUpdate_thenUpdateAndSave() {
        when(productRepository.findById((short) 1)).thenReturn(Optional.of(product));
        when(categoryRepository.findById((short) 1)).thenReturn(Optional.of(category));
        when(supplierRepository.findById((short) 1)).thenReturn(Optional.of(supplier));
        when(productRepository.save(product)).thenReturn(product);
        when(productMapper.toResponseDTO(product)).thenReturn(productResponseDTO);

        ProductResponseDTO result = productService.update((short) 1, productRequestDTO);

        assertNotNull(result);

        verify(productRepository).findById((short) 1);
        verify(productMapper).updateEntityFromDTO(productRequestDTO, product);
        verify(categoryRepository).findById((short) 1);
        verify(supplierRepository).findById((short) 1);
        verify(productRepository).save(product);
    }

    @Test
    void givenExistingId_whenDelete_thenDeleteProduct() {
        when(productRepository.existsById((short) 1)).thenReturn(true);

        productService.delete((short) 1);

        verify(productRepository).existsById((short) 1);
        verify(productRepository).deleteById((short) 1);
    }

    @Test
    void givenNonExistingId_whenDelete_thenThrowException() {
        when(productRepository.existsById((short) 1)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class,
                () -> productService.delete((short) 1));

        verify(productRepository).existsById((short) 1);
        verify(productRepository, never()).deleteById(any());
    }
}
