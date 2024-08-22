package org.yaroslaavl.webappstarter.mapper;

import org.springframework.stereotype.Component;
import org.yaroslaavl.webappstarter.database.entity.MedicalRecord;
import org.yaroslaavl.webappstarter.dto.MedicalRecordReadDto;

@Component
public class MedicalRecordReadMapper implements Mapper<MedicalRecord, MedicalRecordReadDto>{

    @Override
    public MedicalRecordReadDto map(MedicalRecord object) {
        return new MedicalRecordReadDto(
                object.getId(),
                object.getPet(),
                object.getVeterinarian(),
                object.getDiagnosis(),
                object.getTreatment(),
                object.getPrescription(),
                object.getExaminationDate()
        );
    }
}
