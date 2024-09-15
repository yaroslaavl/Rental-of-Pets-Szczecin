package org.yaroslaavl.webappstarter.unit.mapper;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.yaroslaavl.webappstarter.database.entity.Role;
import org.yaroslaavl.webappstarter.database.entity.User;
import org.yaroslaavl.webappstarter.dto.UserCreateEditDto;
import org.yaroslaavl.webappstarter.dto.UserReadDto;
import org.yaroslaavl.webappstarter.mapper.mapStruct.UserMapper;

@SpringBootTest
@RequiredArgsConstructor
public class UserMapperTest {

    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Test
    void userMapperToEntity(){

        UserCreateEditDto userDto = new UserCreateEditDto(8L,"dima@gmail.com","123456Qw",null,"Dima","Dimonenko",null,null,null,null, Role.USER,false,"b32323");

        User entity = userMapper.toEntity(userDto, passwordEncoder);

        System.out.println(entity.getPassword());
        Assertions.assertNotEquals("123456Qw",entity.getPassword());

    }

    @Test
    void userMapperToDto(){

        User user = User.builder()
                .username("dima@gmail.com")
                .password(passwordEncoder.encode("123456Qw"))
                .firstname("Dima")
                .lastname("Dimonenko")
                .emailVerified(false)
                .emailVerificationToken("b32323")
                .build();

        UserReadDto dto = userMapper.toDto(user);

        Assertions.assertEquals(user.getUsername(), dto.getUsername());
        Assertions.assertEquals(user.getFirstname(), dto.getFirstname());
        Assertions.assertEquals(user.getLastname(), dto.getLastname());
        Assertions.assertEquals(user.getEmailVerified(), dto.getEmailVerified());
        Assertions.assertEquals(user.getEmailVerificationToken(), dto.getEmailVerificationToken());
    }

}
