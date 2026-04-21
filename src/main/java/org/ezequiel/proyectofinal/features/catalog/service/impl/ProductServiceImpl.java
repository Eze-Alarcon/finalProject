package org.ezequiel.proyectofinal.features.catalog.service.impl;

import lombok.RequiredArgsConstructor;
import org.ezequiel.proyectofinal.core.exceptions.ResourceNotFoundException;
import org.ezequiel.proyectofinal.features.catalog.dto.*;
import org.ezequiel.proyectofinal.features.catalog.entity.Category;
import org.ezequiel.proyectofinal.features.catalog.entity.Product;
import org.ezequiel.proyectofinal.features.catalog.entity.Supplier;
import org.ezequiel.proyectofinal.features.catalog.mapper.ProductMapper;
import org.ezequiel.proyectofinal.features.catalog.repository.CategoryRepository;
import org.ezequiel.proyectofinal.features.catalog.repository.ProductRepository;
import org.ezequiel.proyectofinal.features.catalog.repository.ProductSpecifications;
import org.ezequiel.proyectofinal.features.catalog.repository.SupplierRepository;
import org.ezequiel.proyectofinal.features.catalog.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final SupplierRepository supplierRepository;
    private final ProductMapper productMapper;

    @Deprecated
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
        resolveSupplier(product, dto.getSupplierId());
        Product saved = productRepository.save(product);
        return productMapper.toResponseDTO(saved);
    }

    @Override
    @Transactional
    public ProductResponseDTO update(Short id, ProductUpdateRequestDTO dto) {
        Product existing = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product", id));
        productMapper.updateEntityFromDTO(dto, existing);
        resolveCategory(existing, dto.getCategoryId());
        resolveSupplier(existing, dto.getSupplierId());
        Product updated = productRepository.save(existing);
        return productMapper.toResponseDTO(updated);
    }

    @Override
    @Transactional
    public ProductResponseDTO restock(Short id, RestockRequestDTO dto) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product", id));

        if (dto.getQuantity() <= 0) {
            throw new IllegalArgumentException("Restock quantity must be greater than 0");
        }

        short quantity = dto.getQuantity().shortValue();
        product.setUnitsInStock((short) (product.getUnitsInStock() + quantity));

        if (product.getUnitsOnOrder() != null) {
            short newOnOrder = (short) (product.getUnitsOnOrder() - quantity);
            product.setUnitsOnOrder(newOnOrder < 0 ? (short) 0 : newOnOrder);
        }

        Product saved = productRepository.save(product);
        return productMapper.toResponseDTO(saved);
    }

    @Override
    @Transactional
    public ProductResponseDTO discontinue(Short id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product", id));

        product.setDiscontinued(1);
        Product saved = productRepository.save(product);
        return productMapper.toResponseDTO(saved);
    }

    @Override
    public Page<ProductResponseDTO> search(Short categoryId, Short supplierId, Float minPrice, Float maxPrice, Boolean inStock, Pageable pageable) {
        Specification<Product> spec = Specification.allOf(
                ProductSpecifications.hasCategoryId(categoryId),
                ProductSpecifications.hasSupplierId(supplierId),
                ProductSpecifications.hasMinPrice(minPrice),
                ProductSpecifications.hasMaxPrice(maxPrice),
                ProductSpecifications.isInStock(inStock),
                ProductSpecifications.isNotDiscontinued()
        );

        return productRepository.findAll(spec, pageable)
                .map(productMapper::toResponseDTO);
    }

    @Override
    public List<ProductLowStockResponseDTO> getLowStockAlerts() {
        return productRepository.findLowStockProducts().stream()
                .map(p -> new ProductLowStockResponseDTO(
                        p.getProductId(),
                        p.getProductName(),
                        p.getUnitsInStock(),
                        p.getReorderLevel(),
                        p.getSupplier() != null ? p.getSupplier().getCompanyName() : null
                ))
                .collect(Collectors.toList());
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

    private void resolveSupplier(Product product, Short supplierId) {
        if (supplierId != null) {
            Supplier supplier = supplierRepository.findById(supplierId)
                    .orElseThrow(() -> new ResourceNotFoundException("Supplier", supplierId));
            product.setSupplier(supplier);
        } else {
            product.setSupplier(null);
        }
    }
}
