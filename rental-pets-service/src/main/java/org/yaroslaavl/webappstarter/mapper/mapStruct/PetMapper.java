package org.yaroslaavl.webappstarter.mapper.mapStruct;

import org.mapstruct.Mapper;
import org.yaroslaavl.webappstarter.database.entity.Pet;
import org.yaroslaavl.webappstarter.dto.PetCreateEditDto;
import org.yaroslaavl.webappstarter.dto.PetReadDto;

@Mapper(componentModel = "spring")
public interface PetMapper {

    PetReadDto toDto(Pet pet);

    Pet toEntity(PetCreateEditDto petCreateEditDto);
}
