package org.yaroslaavl.webappstarter.dto;

import lombok.Value;
import org.yaroslaavl.webappstarter.database.entity.Clinic;

@Value
public class VeterinarianReadDto {

    Long id;
    Clinic clinic;
    String vetCode;
    String firstname;
    String lastname;
    String specialization;
    String contactNumber;

}
