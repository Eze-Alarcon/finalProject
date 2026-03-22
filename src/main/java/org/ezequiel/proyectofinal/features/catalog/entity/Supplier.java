package org.ezequiel.proyectofinal.features.catalog.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "suppliers", schema = "public")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Supplier {

    @Id
    @Column(name = "supplier_id")
    private Short supplierId;

    @Column(name = "company_name", nullable = false, length = 40)
    private String companyName;

    @Column(name = "contact_name", length = 30)
    private String contactName;

    @Column(name = "contact_title", length = 30)
    private String contactTitle;

    @Column(name = "address", length = 60)
    private String address;

    @Column(name = "city", length = 15)
    private String city;

    @Column(name = "region", length = 15)
    private String region;

    @Column(name = "postal_code", length = 10)
    private String postalCode;

    @Column(name = "country", length = 15)
    private String country;

    @Column(name = "phone", length = 24)
    private String phone;

    @Column(name = "fax", length = 24)
    private String fax;

    @Column(name = "homepage", columnDefinition = "TEXT")
    private String homepage;

    @OneToMany(mappedBy = "supplier", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Product> products = new ArrayList<>();
}
