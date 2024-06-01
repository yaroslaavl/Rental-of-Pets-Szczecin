package org.yaroslaavl.webappstarter.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.yaroslaavl.webappstarter.database.entity.MedicalRecord;

public interface MedicalRecordRepository extends JpaRepository<MedicalRecord,Long> {

    @Query("SELECT m FROM MedicalRecord m JOIN FETCH m.pet " +
            "JOIN FETCH m.veterinarian vet " +
            "JOIN FETCH vet.clinic " +
            "where m.id = :medicalRecordId")
    MedicalRecord findByIdWithPetAndVeterinarianAndClinic(@Param("medicalRecordId") Long medicalRecordId);

}
