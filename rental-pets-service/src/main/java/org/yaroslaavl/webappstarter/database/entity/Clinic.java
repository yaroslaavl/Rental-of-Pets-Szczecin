package org.yaroslaavl.webappstarter.database.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(schema = "webapp",name = "clinics")
public class Clinic extends BaseEntity<Long> {

    private String name;

    @Column(nullable = false)
    private String address;

    @Builder.Default
    @OneToMany(mappedBy = "clinic", cascade = CascadeType.ALL)
    private List<Veterinarian> veterinarians = new ArrayList<>();
}
