package org.yaroslaavl.webappstarter.database.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(schema = "webapp",name = "veterinarians")
public class Veterinarian extends BaseEntity<Long> {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "clinic_id")
    private Clinic clinic;

    @Column(unique = true,nullable = false)
    private String vetCode;

    @Column(name = "firstname",nullable = false)
    private String firstname;

    @Column(name = "lastname",nullable = false)
    private String lastname;

    private String specialization;

    private String contactNumber;
}
