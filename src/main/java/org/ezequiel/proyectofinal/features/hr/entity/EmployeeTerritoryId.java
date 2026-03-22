package org.ezequiel.proyectofinal.features.hr.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeTerritoryId implements Serializable {

    @Column(name = "employee_id")
    private Short employeeId;

    @Column(name = "territory_id", length = 20)
    private String territoryId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EmployeeTerritoryId that)) return false;
        return Objects.equals(employeeId, that.employeeId) &&
               Objects.equals(territoryId, that.territoryId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(employeeId, territoryId);
    }
}
