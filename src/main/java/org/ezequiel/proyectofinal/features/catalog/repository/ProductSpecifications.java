package org.ezequiel.proyectofinal.features.catalog.repository;

import org.ezequiel.proyectofinal.features.catalog.entity.Product;
import org.springframework.data.jpa.domain.Specification;

public class ProductSpecifications {

    public static Specification<Product> hasCategoryId(Short categoryId) {
        return (root, query, criteriaBuilder) ->
                categoryId == null ? null : criteriaBuilder.equal(root.get("category").get("categoryId"), categoryId);
    }

    public static Specification<Product> hasSupplierId(Short supplierId) {
        return (root, query, criteriaBuilder) ->
                supplierId == null ? null : criteriaBuilder.equal(root.get("supplier").get("supplierId"), supplierId);
    }

    public static Specification<Product> hasMinPrice(Float minPrice) {
        return (root, query, criteriaBuilder) ->
                minPrice == null ? null : criteriaBuilder.greaterThanOrEqualTo(root.get("unitPrice"), minPrice);
    }

    public static Specification<Product> hasMaxPrice(Float maxPrice) {
        return (root, query, criteriaBuilder) ->
                maxPrice == null ? null : criteriaBuilder.lessThanOrEqualTo(root.get("unitPrice"), maxPrice);
    }

    public static Specification<Product> isInStock(Boolean inStock) {
        return (root, query, criteriaBuilder) -> {
            if (inStock == null) return null;
            return inStock ? criteriaBuilder.greaterThan(root.get("unitsInStock"), 0) : criteriaBuilder.equal(root.get("unitsInStock"), 0);
        };
    }

    public static Specification<Product> isNotDiscontinued() {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("discontinued"), 0);
    }
}
