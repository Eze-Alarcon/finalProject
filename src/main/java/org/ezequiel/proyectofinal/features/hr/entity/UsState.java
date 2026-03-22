package org.ezequiel.proyectofinal.features.hr.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "us_states", schema = "public")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsState {

    @Id
    @Column(name = "state_id")
    private Short stateId;

    @Column(name = "state_name", length = 100)
    private String stateName;

    @Column(name = "state_abbr", length = 2)
    private String stateAbbr;

    @Column(name = "state_region", length = 50)
    private String stateRegion;
}
