package org.yaroslaavl.webappstarter.dto;

import lombok.Value;
import org.yaroslaavl.webappstarter.database.entity.Veterinarian;

import java.util.List;

@Value
public class ClinicReadDto {

    Long id;
    String name;
    String address;
    List<Veterinarian> veterinarians;

}
