package org.ezequiel.proyectofinal.features.hr.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "territories", schema = "public")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Territory {

    @Id
    @Column(name = "territory_id", length = 20)
    private String territoryId;

    @Column(name = "territory_description", nullable = false, length = 60)
    private String territoryDescription;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id", nullable = false)
    private Region region;

    @OneToMany(mappedBy = "territory", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<EmployeeTerritory> employeeTerritories = new ArrayList<>();
}
