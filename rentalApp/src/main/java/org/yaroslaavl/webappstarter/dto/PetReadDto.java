package org.yaroslaavl.webappstarter.dto;

import lombok.Value;
import org.yaroslaavl.webappstarter.database.entity.Gender;
import org.yaroslaavl.webappstarter.database.entity.Species;

import java.util.List;

@Value
public class PetReadDto {
    Long id;
    Species species;
    String breed;
    String name;
    Integer age;
    Gender gender;
    List<MedicalRecordReadDto> medicalRecords;
    String description;
    String imageUrl;
    Boolean isAvailable;

}
