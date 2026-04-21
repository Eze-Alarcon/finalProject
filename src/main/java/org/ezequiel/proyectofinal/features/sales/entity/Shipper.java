package org.ezequiel.proyectofinal.features.sales.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "shippers", schema = "public")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Shipper {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shipper_id")
    private Short shipperId;

    @Column(name = "company_name", nullable = false, length = 40)
    private String companyName;

    @Column(name = "phone", length = 24)
    private String phone;
}
