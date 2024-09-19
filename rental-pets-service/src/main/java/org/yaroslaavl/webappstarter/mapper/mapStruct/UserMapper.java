package org.yaroslaavl.webappstarter.mapper.mapStruct;

import org.mapstruct.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.MultipartFile;
import org.yaroslaavl.webappstarter.database.entity.User;
import org.yaroslaavl.webappstarter.dto.UserCreateEditDto;
import org.yaroslaavl.webappstarter.dto.UserReadDto;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mappings({
            @Mapping(target = "password", expression = "java(passwordEncoder.encode(userCreateEditDto.getPassword()))"),
            @Mapping(target = "profilePicture", source = "userCreateEditDto.profilePicture", qualifiedByName = "mapMultipartFileToString")
    })
    User toEntity(UserCreateEditDto userCreateEditDto, @Context PasswordEncoder passwordEncoder);

    @Named("mapMultipartFileToString")
    default String mapMultipartFileToString(MultipartFile file) {
        return file != null ? file.getOriginalFilename() : null;
    }

    UserReadDto toDto(User user);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "password", ignore = true),
            @Mapping(target = "profilePicture", source = "userCreateEditDto.profilePicture", qualifiedByName = "mapMultipartFileToString")
    })
    void updateEntityFromDto(UserCreateEditDto userCreateEditDto, @MappingTarget User user);
}
