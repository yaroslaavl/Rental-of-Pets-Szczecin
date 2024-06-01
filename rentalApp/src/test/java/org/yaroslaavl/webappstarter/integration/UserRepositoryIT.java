package org.yaroslaavl.webappstarter.integration;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.yaroslaavl.webappstarter.database.entity.User;
import org.yaroslaavl.webappstarter.database.repository.UserRepository;
import org.yaroslaavl.webappstarter.service.MailService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UserRepositoryIT extends TestConfig{

    private UserRepository repository;

    @MockBean
    private MailService mailService;

    @MockBean
    private ClientRegistrationRepository clientRegistrationRepository;

    @Test
    void test_save_user(){
        User user = new User();
        user.setUsername("testUser");
        user.setPassword("password");

        Optional<User> savedUser = repository.findByUsername("testUser");
          if(savedUser.isPresent()){

              assertNotNull(savedUser);
              assertEquals("password", savedUser.get().getPassword());

          }
    }

}
