package org.yaroslaavl.webappstarter.mapper.mapStruct;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.yaroslaavl.webappstarter.database.entity.Pet;
import org.yaroslaavl.webappstarter.dto.PetCreateEditDto;
import org.yaroslaavl.webappstarter.dto.PetReadDto;

@Mapper(componentModel = "spring")
public interface PetMapper {

    PetReadDto toDto(Pet pet);

    @Mapping(target = "id", ignore = true)
    void updateEntityFromDto(PetCreateEditDto petCreateEditDto, @MappingTarget Pet pet);

}
