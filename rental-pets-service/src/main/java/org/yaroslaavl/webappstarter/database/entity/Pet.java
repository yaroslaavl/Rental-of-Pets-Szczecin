package org.yaroslaavl.webappstarter.database.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(schema = "webapp",name = "pets")
@Entity
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

