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
@Table(schema = "webapp",name = "species")
public class Species extends BaseEntity<Long> {

    @Column(unique = true,nullable = false)
    private String name;

}
