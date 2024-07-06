package org.yaroslaavl.webappstarter.unit.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.Assertions.assertThat;
import org.yaroslaavl.webappstarter.database.entity.Role;
import org.yaroslaavl.webappstarter.database.entity.User;
import org.yaroslaavl.webappstarter.database.repository.UserRepository;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserRepositoryTest {

    @Mock
    private UserRepository userRepository;

    @Test
    public void save() {
        User user = User.builder()
                .username("ylopatkin1@gmail.com")
                .password("123passwordA")
                .firstname("User")
                .lastname("User")
                .role(Role.USER)
                .emailVerified(Boolean.FALSE)
                .build();

        User user2 = User.builder()
                .username("ylopatkin2@gmail.com")
                .password("123passwordA")
                .firstname("User")
                .lastname("User")
                .role(Role.USER)
                .emailVerified(Boolean.FALSE)
                .build();

        when(userRepository.findAll()).thenReturn(Arrays.asList(user,user2));

        List<User> users = userRepository.findAll();

        assertThat(users).isNotNull();
        assertThat(users).hasSize(2);
        assertThat(users).contains(user, user2);
    }

    @Test
    public void find_by_username(){
        String name = "ylopatkin@gmail.com";

        User user = User.builder()
                .username(name)
                .password("123passwordA")
                .firstname("User")
                .lastname("User")
                .role(Role.USER)
                .emailVerified(Boolean.FALSE)
                .build();

        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));

        Optional<User> byUsername = userRepository.findByUsername(user.getUsername());

        assertThat(byUsername).isPresent();
        assertThat(byUsername.get().getUsername()).isEqualTo(name);
    }

    @Test
    public void find_by_email_verification_code() {
        String code = "132131241SADAdsffg";

        User user = User.builder()
                .username("ylopatkin@gmail.com")
                .password("123passwordA")
                .firstname("User")
                .lastname("User")
                .role(Role.USER)
                .emailVerified(Boolean.FALSE)
                .emailVerificationToken("132131241SADAdsffg")
                .build();

        when(userRepository.findByEmailVerificationToken(code)).thenReturn(user);

        User byEmailVerificationToken = userRepository.findByEmailVerificationToken(code);

        assertThat(byEmailVerificationToken).isNotNull();
        assertThat(byEmailVerificationToken.getEmailVerificationToken()).isEqualTo(code);
    }

}
