package org.yaroslaavl.webappstarter.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.yaroslaavl.webappstarter.database.entity.User;
import org.yaroslaavl.webappstarter.dto.UserReadDto;

@Component
@RequiredArgsConstructor
public class UserReadMapper implements Mapper<User, UserReadDto> {
    @Override
    public UserReadDto map(User object) {
        return new UserReadDto(
                object.getId(),
                object.getUsername(),
                object.getPassword(),
                object.getBirthDate(),
                object.getFirstname(),
                object.getLastname(),
                object.getAddress(),
                object.getPesel(),
                object.getPhone(),
                object.getProfilePicture(),
                object.getRole(),
                object.getEmailVerified(),
                object.getEmailVerificationToken()
        );
    }
}
