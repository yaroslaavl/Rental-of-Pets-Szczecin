package org.yaroslaavl.webappstarter.mapper;

import org.springframework.stereotype.Component;
import org.yaroslaavl.webappstarter.database.entity.Species;
import org.yaroslaavl.webappstarter.dto.SpeciesReadDto;

@Component
public class SpeciesReadMapper implements Mapper<Species, SpeciesReadDto> {
    @Override
    public SpeciesReadDto map(Species object) {
        return new SpeciesReadDto(
                object.getId(),
                object.getName()
        );
    }
}
