package org.yaroslaavl.webappstarter.dto;

import lombok.Value;
import org.yaroslaavl.webappstarter.database.entity.Pet;
import org.yaroslaavl.webappstarter.database.entity.Veterinarian;

import java.time.LocalDate;

@Value
public class MedicalRecordReadDto {

    Long id;
    Pet pet;
    Veterinarian veterinarian;
    String diagnosis;
    String treatment;
    String prescription;
    LocalDate examinationDate;
}
