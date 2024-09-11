package org.yaroslaavl.webappstarter.database.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(schema = "webapp", name = "users")
public class User extends BaseEntity<Long> {

    @Column(unique = true, nullable = false,name = "username")
    private String username;

    @Column(nullable = false, name = "password")
    private String password;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(name = "firstname")
    private String firstname;

    @Column(name = "lastname")
    private String lastname;

    private String address;

    @Column(unique = true)
    private String pesel;

    @Column(unique = true)
    private String phone;

    @Column(name = "profile_picture")
    private String profilePicture;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "email_verified", nullable = false)
    private Boolean emailVerified;

    @Column(name = "email_verification_token",nullable = false)
    private String emailVerificationToken;

}
