package org.yaroslaavl.webappstarter.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.yaroslaavl.webappstarter.database.entity.Pet;
import org.yaroslaavl.webappstarter.dto.MedicalRecordReadDto;
import org.yaroslaavl.webappstarter.dto.PetReadDto;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PetReadMapper implements Mapper<Pet, PetReadDto> {

    private final MedicalRecordReadMapper medicalRecordReadMapper;

    @Override
    public PetReadDto map(Pet object) {
        List<MedicalRecordReadDto> medicalRecordDtos = object.getMedicalRecords().stream()
                .map(medicalRecordReadMapper::map)
                .collect(Collectors.toList());
        return new PetReadDto(
                object.getId(),
                object.getSpecies(),
                object.getBreed(),
                object.getName(),
                object.getAge(),
                object.getGender(),
                medicalRecordDtos,
                object.getDescription(),
                object.getImageUrl(),
                object.getIsAvailable()
        );
    }
}
