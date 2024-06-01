package org.yaroslaavl.webappstarter.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.yaroslaavl.webappstarter.database.entity.User;
import org.yaroslaavl.webappstarter.dto.UserCreateEditDto;
import java.util.Optional;
import static java.util.function.Predicate.not;

@Component
@RequiredArgsConstructor
public class UserCreateEditMapper implements Mapper<UserCreateEditDto,User> {

    private final PasswordEncoder passwordEncoder;

    @Override
    public User map(UserCreateEditDto fromObject,User toObject){
        copy(fromObject,toObject);
        return toObject;
    }
    @Override
    public User map(UserCreateEditDto object) {
        User user = new User();
        copy(object,user);
        return user;
    }

    private void copy(UserCreateEditDto object, User user) {
        user.setId(object.getId());
        user.setUsername(object.getUsername());
        user.setBirthDate(object.getBirthDate());
        user.setFirstname(object.getFirstname());
        user.setLastname(object.getLastname());
        user.setAddress(object.getAddress());
        user.setPesel(object.getPesel());
        user.setPhone(object.getPhone());
        user.setRole(object.getRole());
        user.setEmailVerified(object.getEmailVerified());
        user.setEmailVerificationToken(object.getEmailVerificationToken());

        Optional.ofNullable(object.getPassword())
                .filter(StringUtils::hasText)
                .map(passwordEncoder::encode)
                .ifPresent(user::setPassword);

        Optional.ofNullable(object.getProfilePicture())
                .filter(not(MultipartFile::isEmpty))
                .ifPresent(multipartFile -> user.setProfilePicture(multipartFile.getOriginalFilename()));
    }
}
