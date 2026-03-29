package org.ezequiel.proyectofinal.features.sales.entity;

import jakarta.persistence.*;
import lombok.*;
import org.ezequiel.proyectofinal.features.hr.entity.Employee;

import java.time.LocalDate;

@Entity
@Table(name = "orders", schema = "public")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Short orderId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @Column(name = "order_date")
    private LocalDate orderDate;

    @Column(name = "required_date")
    private LocalDate requiredDate;

    @Column(name = "shipped_date")
    private LocalDate shippedDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ship_via")
    private Shipper shipper;

    @Column(name = "freight")
    private Float freight;

    @Column(name = "ship_name", length = 40)
    private String shipName;

    @Column(name = "ship_address", length = 60)
    private String shipAddress;

    @Column(name = "ship_city", length = 15)
    private String shipCity;

    @Column(name = "ship_region", length = 15)
    private String shipRegion;

    @Column(name = "ship_postal_code", length = 10)
    private String shipPostalCode;

    @Column(name = "ship_country", length = 15)
    private String shipCountry;
}
