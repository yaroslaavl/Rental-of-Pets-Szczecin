package org.yaroslaavl.webappstarter.mapper.mapStruct;

import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
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
    User toEntity(UserCreateEditDto userCreateEditDto, PasswordEncoder passwordEncoder);

    @Named("mapMultipartFileToString")
    default String mapMultipartFileToString(MultipartFile file) {
        return file != null ? file.getOriginalFilename() : null;
    }

    UserReadDto toDto(User user);

}
