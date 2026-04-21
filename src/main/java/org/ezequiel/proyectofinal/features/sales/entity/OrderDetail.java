package org.ezequiel.proyectofinal.features.sales.entity;

import jakarta.persistence.*;
import lombok.*;
import org.ezequiel.proyectofinal.features.catalog.entity.Product;

@Entity
@Table(name = "order_details", schema = "public")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetail {

    @EmbeddedId
    private OrderDetailId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("orderId")
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("productId")
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "unit_price", nullable = false)
    private Float unitPrice;

    @Column(name = "quantity", nullable = false)
    private Short quantity;

    @Column(name = "discount", nullable = false)
    private Float discount;
}
