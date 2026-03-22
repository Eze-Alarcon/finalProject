package org.ezequiel.proyectofinal.features.catalog.service.impl;

import lombok.RequiredArgsConstructor;
import org.ezequiel.proyectofinal.core.exceptions.ResourceNotFoundException;
import org.ezequiel.proyectofinal.features.catalog.dto.ProductRequestDTO;
import org.ezequiel.proyectofinal.features.catalog.dto.ProductResponseDTO;
import org.ezequiel.proyectofinal.features.catalog.entity.Category;
import org.ezequiel.proyectofinal.features.catalog.entity.Product;
import org.ezequiel.proyectofinal.features.catalog.mapper.ProductMapper;
import org.ezequiel.proyectofinal.features.catalog.repository.CategoryRepository;
import org.ezequiel.proyectofinal.features.catalog.repository.ProductRepository;
import org.ezequiel.proyectofinal.features.catalog.service.ProductService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper productMapper;

    @Override
    public List<ProductResponseDTO> findAll() {
        return productRepository.findAll()
                .stream()
                .map(productMapper::toResponseDTO)
                .toList();
    }

    @Override
    public ProductResponseDTO findById(Short id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product", id));
        return productMapper.toResponseDTO(product);
    }

    @Override
    @Transactional
    public ProductResponseDTO save(ProductRequestDTO dto) {
        Product product = productMapper.toEntity(dto);
        resolveCategory(product, dto.getCategoryId());
        Product saved = productRepository.save(product);
        return productMapper.toResponseDTO(saved);
    }

    @Override
    @Transactional
    public ProductResponseDTO update(Short id, ProductRequestDTO dto) {
        Product existing = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product", id));
        productMapper.updateEntityFromDTO(dto, existing);
        resolveCategory(existing, dto.getCategoryId());
        Product updated = productRepository.save(existing);
        return productMapper.toResponseDTO(updated);
    }

    @Override
    @Transactional
    public void delete(Short id) {
        if (!productRepository.existsById(id)) {
            throw new ResourceNotFoundException("Product", id);
        }
        productRepository.deleteById(id);
    }

    private void resolveCategory(Product product, Short categoryId) {
        if (categoryId != null) {
            Category category = categoryRepository.findById(categoryId)
                    .orElseThrow(() -> new ResourceNotFoundException("Category", categoryId));
            product.setCategory(category);
        } else {
            product.setCategory(null);
        }
    }
}
