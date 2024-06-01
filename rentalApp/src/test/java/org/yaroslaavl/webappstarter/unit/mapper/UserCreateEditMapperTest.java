package org.yaroslaavl.webappstarter.unit.mapper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.yaroslaavl.webappstarter.database.entity.User;
import org.yaroslaavl.webappstarter.dto.UserCreateEditDto;
import org.yaroslaavl.webappstarter.mapper.UserCreateEditMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserCreateEditMapperTest {

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserCreateEditMapper userCreateEditMapper;

    @Test
    void map_UserCreateEditDtoToUser_ShouldMapCorrectly() {
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");

        var expectedUser = new User();
        expectedUser.setUsername("test");
        expectedUser.setPassword("encodedPassword");

        var userCreateEditDto = new UserCreateEditDto(2L,"test", "encodedPassword", null, null, null, null, null,null,null,null,null,null);
        var actualUser = userCreateEditMapper.map(userCreateEditDto);

        assertEquals(expectedUser.getUsername(), actualUser.getUsername());
        assertTrue(passwordEncoder.matches("encodedPassword", actualUser.getPassword()));
    }

}