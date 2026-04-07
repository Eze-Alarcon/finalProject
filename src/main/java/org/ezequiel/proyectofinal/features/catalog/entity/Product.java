package org.ezequiel.proyectofinal.features.catalog.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.boot.context.properties.bind.DefaultValue;

@Entity
@Table(name = "products", schema = "public")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Short productId;

    @Column(name = "product_name", nullable = false, length = 40)
    private String productName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(name = "quantity_per_unit", length = 20)
    private String quantityPerUnit;

    @Column(name = "unit_price")
    private Float unitPrice;

    @Column(name = "units_in_stock")
    private Short unitsInStock;

    @Column(name = "units_on_order")
    private Short unitsOnOrder = 0;

    @Column(name = "reorder_level")
    private Short reorderLevel = 0;

    @Column(name = "discontinued", nullable = false)
    private Integer discontinued = 0;
}
