package org.yaroslaavl.webappstarter.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.yaroslaavl.webappstarter.database.entity.Clinic;
import org.yaroslaavl.webappstarter.dto.ClinicReadDto;

@Component
@RequiredArgsConstructor
public class ClinicReadMapper implements Mapper<Clinic, ClinicReadDto> {

    @Override
    public ClinicReadDto map(Clinic object) {
        return new ClinicReadDto(
                object.getId(),
                object.getName(),
                object.getAddress(),
                object.getVeterinarians()
        );
    }
}
