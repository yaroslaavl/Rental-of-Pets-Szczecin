package org.yaroslaavl.webappstarter.database.entity;

import lombok.*;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(schema = "webapp",name = "species")
public class Species extends BaseEntity<Long> {

    @Column(unique = true,nullable = false)
    private String name;

}
