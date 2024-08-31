package org.yaroslaavl.webappstarter.unit.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;
import org.yaroslaavl.webappstarter.database.entity.Role;
import org.yaroslaavl.webappstarter.database.entity.User;
import org.yaroslaavl.webappstarter.database.repository.UserRepository;
import org.yaroslaavl.webappstarter.dto.UserCreateEditDto;
import org.yaroslaavl.webappstarter.dto.UserReadDto;
import org.yaroslaavl.webappstarter.mapper.UserCreateEditMapper;
import org.yaroslaavl.webappstarter.mapper.UserReadMapper;
import org.yaroslaavl.webappstarter.service.ImageService;
import org.yaroslaavl.webappstarter.service.MailService;
import org.yaroslaavl.webappstarter.service.UserService;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Optional;
import java.util.UUID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@Slf4j
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private MailService mailService;

    @Captor
    private ArgumentCaptor<String> emailCaptor;

    @Captor
    private ArgumentCaptor<String> subjectCaptor;

    @Captor
    private ArgumentCaptor<String> messageCaptor;

    @Mock
    private UserCreateEditMapper userCreateEditMapper;

    @Mock
    private UserReadMapper userReadMapper;

    @InjectMocks
    private UserService userService;

    private User user;

    @BeforeEach
    public void setUp() {
        String activationToken = UUID.randomUUID().toString();

        user = User.builder()
                .username("ylopatkin@gmail.com")
                .password("123passwordA")
                .firstname("Dakart")
                .lastname("Dakartovich")
                .address(null)
                .pesel(null)
                .phone(null)
                .profilePicture(null)
                .role(Role.USER)
                .emailVerified(Boolean.FALSE)
                .emailVerificationToken(activationToken)
                .build();
    }

    @Test
    public void create_user() throws UnknownHostException {

        InetAddress localhost = InetAddress.getLocalHost();
        String ip = localhost.getHostAddress();

        String activationToken = UUID.randomUUID().toString();
        UserCreateEditDto userCreateEditDto = new UserCreateEditDto(
                2L,
                "test@gmail.com",
                "32343ADwewf",
                null,
                "Dakart",
                "Dakartovich",
                null,
                null,
                null,
                null,
                Role.USER,
                false,
                null
        );

        UserReadDto userReadDto = new UserReadDto(
                3L,
                "test@gmail.com",
                null,
                null,
                "Dakart",
                "Dakartovich",
                null,
                null,
                null,
                null,
                Role.USER,
                false,
                activationToken
        );

        when(userCreateEditMapper.map(any(UserCreateEditDto.class))).thenReturn(user);
        when(userRepository.saveAndFlush(any(User.class))).thenReturn(user);
        when(userReadMapper.map(any(User.class))).thenReturn(userReadDto);

        UserReadDto readDto = userService.create(userCreateEditDto);

        verify(mailService).send(emailCaptor.capture(), subjectCaptor.capture(), messageCaptor.capture());

        log.info(messageCaptor.getValue());

        Assertions.assertEquals("test@gmail.com", emailCaptor.getValue());
        Assertions.assertEquals("Activation code", subjectCaptor.getValue());
        Assertions.assertEquals("Hello, " + userReadDto.getUsername()+"!"+ " \n" +
                "Welcome to our family. Please, visit the following link to activate your account:  http://" + ip + ":8080/activate?token=" + user.getEmailVerificationToken(), messageCaptor.getValue());
        Assertions.assertEquals(userReadDto, readDto);
    }

    @Test
    public void resend_activation_code() throws UnknownHostException {
        InetAddress localhost = InetAddress.getLocalHost();
        String ip = localhost.getHostAddress();

        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);

        userService.resendActivationCode(user.getUsername());

        verify(mailService).send(emailCaptor.capture(), subjectCaptor.capture(), messageCaptor.capture());

        log.info(messageCaptor.getValue());

        Assertions.assertEquals("ylopatkin@gmail.com", emailCaptor.getValue());
        Assertions.assertEquals("Activation code", subjectCaptor.getValue());
        Assertions.assertEquals("Hello, " + user.getUsername()+"!"+ " \n" +
                "Welcome to our family. Please, visit the following link to activate your account: http://" + ip + ":8080/activate?token=" + user.getEmailVerificationToken(), messageCaptor.getValue());

    }

    @Test
    public void activation_user_account(){
        String activationToken = UUID.randomUUID().toString();

        when(userRepository.findByEmailVerificationToken(activationToken)).thenReturn(user);
        when(userRepository.save(any(User.class))).thenReturn(user);

        boolean activated = userService.activation(activationToken);

        verify(userRepository, times(1)).findByEmailVerificationToken(activationToken);
        verify(userRepository, times(1)).save(user);

        assertThat(activated).isTrue();
        assertThat(user.getEmailVerified()).isTrue();
    }

    @Test
    public void update_user_details() {
        UserCreateEditDto userCreateEditDto = new UserCreateEditDto(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                user.getBirthDate(),
                user.getFirstname(),
                user.getLastname(),
                user.getAddress(),
                user.getPesel(),
                user.getPhone(),
                null,
                user.getRole(),
                user.getEmailVerified(),
                user.getEmailVerificationToken()
        );

        User updatedUser = user.toBuilder()
                .username("ylopatk321in@gmail.com")
                .password(user.getPassword())
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .address(user.getAddress())
                .pesel(user.getPesel())
                .phone(user.getPhone())
                .profilePicture(user.getProfilePicture())
                .role(user.getRole())
                .emailVerified(user.getEmailVerified())
                .emailVerificationToken(user.getEmailVerificationToken())
                .build();

        UserReadDto userReadDto = new UserReadDto(
                updatedUser.getId(),
                updatedUser.getUsername(),
                updatedUser.getPassword(),
                updatedUser.getBirthDate(),
                updatedUser.getFirstname(),
                updatedUser.getLastname(),
                updatedUser.getAddress(),
                updatedUser.getPesel(),
                updatedUser.getPhone(),
                updatedUser.getProfilePicture(),
                updatedUser.getRole(),
                updatedUser.getEmailVerified(),
                updatedUser.getEmailVerificationToken()
        );

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(userCreateEditMapper.map(eq(userCreateEditDto), any(User.class))).thenReturn(updatedUser);
        when(userRepository.saveAndFlush(any(User.class))).thenReturn(updatedUser);
        when(userReadMapper.map(any(User.class))).thenReturn(userReadDto);

        Optional<UserReadDto> update = userService.update(user.getId(), userCreateEditDto);

        verify(userRepository).findById(user.getId());
        verify(userCreateEditMapper).map(eq(userCreateEditDto), any(User.class));
        verify(userRepository).saveAndFlush(updatedUser);
        verify(userReadMapper).map(updatedUser);

        log.info(updatedUser.getUsername());

        assertThat(update).isPresent();
        assertThat(update.get()).isEqualTo(userReadDto);
        assertThat(update.get().getUsername()).isEqualTo(userReadDto.getUsername());
    }

    @Test
    public void delete_user(){
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        doNothing().when(userRepository).delete(user);
        doNothing().when(userRepository).flush();

        userService.delete(user.getId());

        verify(userRepository,times(1)).findById(user.getId());
        verify(userRepository, times(1)).delete(user);
        verify(userRepository, times(1)).flush();

        when(userRepository.findById(user.getId())).thenReturn(Optional.empty());

        Optional<User> deletedUser = userRepository.findById(user.getId());
        Assertions.assertFalse(deletedUser.isPresent());
    }
}
