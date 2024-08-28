package org.yaroslaavl.webappstarter.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.yaroslaavl.webappstarter.database.entity.Pet;
import org.yaroslaavl.webappstarter.database.repository.MedicalRecordRepository;
import org.yaroslaavl.webappstarter.dto.PetCreateEditDto;

@Component
@RequiredArgsConstructor
public class PetCreateEditMapper implements Mapper<PetCreateEditDto, Pet>{

    @Override
    public Pet map(PetCreateEditDto object) {
        Pet pet = new Pet();
        copy(object,pet);
        return pet;
    }

    @Override
    public Pet map(PetCreateEditDto fromObject, Pet toObject) {
        copy(fromObject,toObject);
        return toObject;
    }

    private void copy(PetCreateEditDto object, Pet pet) {
        pet.setSpecies(object.getSpecies());
        pet.setBreed(object.getBreed());
        pet.setName(object.getName());
        pet.setAge(object.getAge());
        pet.setGender(object.getGender());
    }
}
