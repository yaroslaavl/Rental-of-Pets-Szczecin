package org.yaroslaavl.webappstarter.dto;

import lombok.Builder;
import lombok.Value;
import lombok.experimental.FieldNameConstants;
import org.hibernate.validator.constraints.pl.PESEL;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;
import org.testcontainers.shaded.org.apache.commons.lang3.RandomStringUtils;
import org.yaroslaavl.webappstarter.database.entity.Role;
import org.yaroslaavl.webappstarter.validation.*;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.Random;

@Value
@FieldNameConstants
public class UserCreateEditDto {

    Long id;

    @NotBlank(message = "{registration.fields.error.email.not.blank}",groups = {CreateAction.class})
    @Email(message = "{registration.fields.error.email}",groups = {CreateAction.class})
    String username;

    @NotBlank(message = "{registration.fields.error.password.not.blank}",groups = {CreateAction.class})
    @Size(min = 8,max = 20,message = "{registration.fields.error.password}",groups = {CreateAction.class})
    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,}$", message = "{booking.fields.error.password}",groups = {CreateAction.class})
    String password;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @AgeLimit(minAge = 16, groups = {EditAction.class},message = "{setting.birth.date}")
    LocalDate birthDate;

    @Size(min = 2, max = 20,message = "{registration.fields.error.firstname.size}",groups = {CreateAction.class,EditAction.class,EditForOrderAction.class})
    @Pattern(regexp = "[A-Za-z]+", message = "{booking.fields.error.firstname.regex}",groups = {CreateAction.class,EditAction.class,EditForOrderAction.class})
    String firstname;

    @Size(min = 2, max = 25,message = "{registration.fields.error.lastname.size}",groups = {CreateAction.class,EditAction.class,EditForOrderAction.class})
    @Pattern(regexp = "[A-Za-z]+", message = "{booking.fields.error.lastname.regex}",groups = {CreateAction.class,EditAction.class,EditForOrderAction.class})
    String lastname;

    @NotBlank(message = "{booking.fields.error.address.not.blank}",groups = {EditForOrderAction.class})
    String address;

    @PESEL(message = "{booking.fields.error.pesel}",groups = {EditForOrderAction.class})
    String pesel;

    @ContactNumber(groups = {EditForOrderAction.class})
    String phone;

    @ImageValid(groups = {ImageAction.class}, message = "{setting.image}")
    MultipartFile profilePicture;

    Role role;

    Boolean emailVerified;

    String emailVerificationToken;

    public static UserCreateEditDto createNewUser(String username, LocalDate birthDate, String firstname, String lastname, String address, String pesel, String phone, MultipartFile profilePicture,Role role,boolean emailVerified,String emailVerificationToken) {
        Random random = new Random();
        int passwordLength = random.nextInt(12) + 5;
        String defaultPassword = RandomStringUtils.randomAlphanumeric(passwordLength);
        return new UserCreateEditDto(null, username, defaultPassword, birthDate, firstname, lastname, address, pesel, phone, profilePicture, role, false, emailVerificationToken);
    }
}
