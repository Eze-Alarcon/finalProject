package org.ezequiel.proyectofinal.features.hr.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "employee_territories", schema = "public")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeTerritory {

    @EmbeddedId
    private EmployeeTerritoryId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("employeeId")
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("territoryId")
    @JoinColumn(name = "territory_id")
    private Territory territory;
}
