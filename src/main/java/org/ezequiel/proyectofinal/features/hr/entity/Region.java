package org.ezequiel.proyectofinal.features.hr.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "region", schema = "public")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Region {

    @Id
    @Column(name = "region_id")
    private Short regionId;

    @Column(name = "region_description", nullable = false, length = 60)
    private String regionDescription;

    @OneToMany(mappedBy = "region", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Territory> territories = new ArrayList<>();
}
