package org.yaroslaavl.webappstarter.integration;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.yaroslaavl.webappstarter.controller.UserController;
import org.yaroslaavl.webappstarter.database.entity.Role;
import org.yaroslaavl.webappstarter.dto.UserCreateEditDto;
import org.yaroslaavl.webappstarter.dto.UserReadDto;
import org.yaroslaavl.webappstarter.service.MailService;
import org.yaroslaavl.webappstarter.service.UserService;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;


@RequiredArgsConstructor
public class UserControllerIT extends TestConfig{

    MockMvc mockMvc;

    private final UserService userService;

    @MockBean
    private MailService mailService;

    @MockBean
    private ClientRegistrationRepository clientRegistrationRepository;

    @MockBean
    private UserController userController;

    @Test
    void create_user_registration_controller() {
        UserCreateEditDto userCreateEditDto = new UserCreateEditDto(
                2L,
                "test@gmail.com",
                "test",
                LocalDate.now(),
                "Test",
                "Testova",
                "Chopina 55",
                "05221812331",
                "883430263",
                new MockMultipartFile("test", new byte[0]),
                Role.USER,
                true,
                "31234384rgh42332"
        );
        UserReadDto userReadDto = userService.create(userCreateEditDto);
        assertEquals(userCreateEditDto.getUsername(), userReadDto.getUsername());
        assertEquals(userCreateEditDto.getPassword(), userReadDto.getPassword());
        assertEquals(userCreateEditDto.getBirthDate(), userReadDto.getBirthDate());
        assertEquals(userCreateEditDto.getFirstname(), userReadDto.getFirstname());
        assertEquals(userCreateEditDto.getLastname(), userReadDto.getLastname());
        assertEquals(userCreateEditDto.getProfilePicture(), userReadDto.getProfilePicture());
        assertEquals(userCreateEditDto.getRole(), userReadDto.getRole());
    }

    @Test
    public void testRegistrationPage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/registration"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("user/registration"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("user"));
    }
}
