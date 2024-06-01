package org.yaroslaavl.webappstarter.mapper;

import org.springframework.stereotype.Component;
import org.yaroslaavl.webappstarter.database.entity.Veterinarian;
import org.yaroslaavl.webappstarter.dto.VeterinarianReadDto;

@Component
public class VeterinarianReadMapper implements Mapper<Veterinarian, VeterinarianReadDto> {

    @Override
    public VeterinarianReadDto map(Veterinarian object) {
        return new VeterinarianReadDto(
                object.getId(),
                object.getClinic(),
                object.getVetCode(),
                object.getFirstname(),
                object.getLastname(),
                object.getSpecialization(),
                object.getContactNumber()
        );
    }
}
