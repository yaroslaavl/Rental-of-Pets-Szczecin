package org.yaroslaavl.webappstarter.dto;

import lombok.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.yaroslaavl.webappstarter.database.entity.Role;
import java.time.LocalDate;
@Value
public class UserReadDto {

    Long id;
    String username;
    String password;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate birthDate;
    String firstname;
    String lastname;
    String address;
    String pesel;
    String phone;
    String profilePicture;
    Role role;
    Boolean emailVerified;
    String emailVerificationToken;
}
