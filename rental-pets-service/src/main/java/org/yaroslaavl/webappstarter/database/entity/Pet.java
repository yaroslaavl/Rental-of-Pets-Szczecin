package org.yaroslaavl.webappstarter.database.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(schema = "webapp",name = "pets")
public class Pet extends BaseEntity<Long> {

    @ManyToOne
    @JoinColumn(name = "species_id")
    private Species species;

    private String breed;

    @Column(unique = true)
    private String name;

    private Integer age;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @OneToMany(mappedBy = "pet", cascade = CascadeType.ALL)
    private List<MedicalRecord> medicalRecords = new ArrayList<>();

    private String description;

    @Column(name = "image_url")
    private String imageUrl;

    private Boolean isAvailable;

}

