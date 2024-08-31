package org.yaroslaavl.webappstarter.unit.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.yaroslaavl.webappstarter.database.entity.Clinic;
import org.yaroslaavl.webappstarter.database.entity.MedicalRecord;
import org.yaroslaavl.webappstarter.database.entity.Pet;
import org.yaroslaavl.webappstarter.database.entity.Veterinarian;
import org.yaroslaavl.webappstarter.database.repository.MedicalRecordRepository;
import static org.assertj.core.api.Assertions.assertThat;


import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MedicalRecordRepositoryTest {

    @Mock
    private MedicalRecordRepository medicalRecordRepository;

    @Test
    public void find_by_id_with_pet_and_veterinarian_and_clinic(){


        Pet pet = Pet.builder()
                .age(2)
                .breed("Dog")
                .name("Jarek")
                .isAvailable(true)
                .build();

        Clinic clinic = Clinic.builder()
                .name("Clinic for pets")
                .address("Chopina")
                .build();

        Veterinarian veterinarian = Veterinarian.builder()
                .firstname("Dima")
                .lastname("Sacha")
                .vetCode("32HBM")
                .clinic(clinic)
                .build();

        MedicalRecord medicalRecord = MedicalRecord.builder()
                .pet(pet)
                .veterinarian(veterinarian)
                .build();

        when(medicalRecordRepository.findByIdWithPetAndVeterinarianAndClinic(medicalRecord.getId())).thenReturn(medicalRecord);

        MedicalRecord foundMedicalRecord  = medicalRecordRepository.findByIdWithPetAndVeterinarianAndClinic(medicalRecord.getId());

        assertThat(foundMedicalRecord.getPet()).isNotNull();
        assertThat(foundMedicalRecord.getPet().getId()).isEqualTo(2L);
        assertThat(foundMedicalRecord.getPet().getName()).isEqualTo("Jarek");

        assertThat(foundMedicalRecord.getVeterinarian()).isNotNull();
        assertThat(foundMedicalRecord.getVeterinarian().getId()).isEqualTo(1L);
        assertThat(foundMedicalRecord.getVeterinarian().getFirstname()).isEqualTo("Dima");

        assertThat(foundMedicalRecord.getVeterinarian().getClinic()).isNotNull();
        assertThat(foundMedicalRecord.getVeterinarian().getClinic().getId()).isEqualTo(3L);
        assertThat(foundMedicalRecord.getVeterinarian().getClinic().getName()).isEqualTo("Clinic for pets");

        assertThat(foundMedicalRecord).isNotNull();
        assertThat(foundMedicalRecord.getId()).isEqualTo(3L);

    }

}
