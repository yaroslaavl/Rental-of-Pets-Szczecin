package org.yaroslaavl.webappstarter.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.yaroslaavl.webappstarter.database.entity.Role;
import org.yaroslaavl.webappstarter.database.entity.User;
import org.yaroslaavl.webappstarter.database.repository.UserRepository;
import org.yaroslaavl.webappstarter.dto.UserCreateEditDto;
import org.yaroslaavl.webappstarter.dto.UserReadDto;
import org.yaroslaavl.webappstarter.mapper.UserCreateEditMapper;
import org.yaroslaavl.webappstarter.mapper.UserReadMapper;

import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserCreateEditMapper userCreateEditMapper;
    private final UserReadMapper userReadMapper;
    private final ImageService imageService;
    private final MailService mailService;

    @Transactional
    public UserReadDto create(UserCreateEditDto userDto) {
        String activationToken = UUID.randomUUID().toString();

        UserReadDto userReadDto = Optional.of(userDto)
                .map(dto -> {
                    uploadImage(dto.getProfilePicture());
                    User user = userCreateEditMapper.map(dto);
                    user.setRole(Role.USER);
                    user.setEmailVerificationToken(activationToken);
                    user.setEmailVerified(false);
                    return userRepository.saveAndFlush(user);
                })
                .map(userReadMapper::map)
                .orElseThrow();

        if (!StringUtils.isEmpty(userDto.getUsername())) {
            String message = String.format(
                    "Hello, %s! \n" +
                            "Welcome to our family. Please, visit the following link to activate your account: http://localhost:8080/activate?token=%s",
                    userDto.getUsername(),
                    activationToken
            );
            mailService.send(userDto.getUsername(), "Activation code", message);
        }

        return userReadDto;
    }
    @Transactional
    public boolean resendActivationCode(String username) {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            String activationToken = UUID.randomUUID().toString();
            user.setEmailVerificationToken(activationToken);
            user.setEmailVerified(false);
            userRepository.save(user);

            if (!StringUtils.isEmpty(user.getUsername())) {
                String message = String.format(
                        "Hello, %s! \n" +
                                "Welcome to our family. Please, visit the following link to activate your account: http://localhost:8080/activate?token=%s",
                        user.getUsername(),
                        activationToken
                );
                mailService.send(user.getUsername(), "Activation code", message);
                return true;
            }
        }
        return false;
    }

    @Transactional
    public boolean activation(String activationToken){
        User user = userRepository.findByEmailVerificationToken(activationToken);

        if(user != null){
            user.setEmailVerified(true);
            userRepository.save(user);
            return true;
        } else {
            throw new IllegalArgumentException("Not found");
        }
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<UserReadDto> findById(Long id){
        return userRepository.findById(id)
                .map(userReadMapper::map);
    }

    public User findUserById(Long id){
        Optional<User> optionalUser = userRepository.findById(id);
        return optionalUser.orElse(null);
    }

    @SneakyThrows
    private void uploadImage(MultipartFile image){
        if(image != null && !image.isEmpty()){
            imageService.upload(image.getOriginalFilename(),image.getInputStream());
        }
    }

    public Optional<byte[]> findAvatar(Long id) {
        return userRepository.findById(id)
                .map(User::getProfilePicture)
                .filter(StringUtils::hasText)
                .flatMap(imageService::get);
    }

    @Transactional
    public Optional<UserReadDto> update(Long id,UserCreateEditDto userCreateEditDto){
        return userRepository.findById(id)
                .map(user -> {
                    uploadImage(userCreateEditDto.getProfilePicture());
                    return userCreateEditMapper.map(userCreateEditDto,user);
                })
                .map(userRepository::saveAndFlush)
                .map(userReadMapper::map);
    }

    @Transactional
    public boolean delete(Long id){
        return userRepository.findById(id)
                .map(entity -> {
                    userRepository.delete(entity);
                    userRepository.flush();
                    return true;
                })
                .orElse(false);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (username == null || username.isEmpty()) {
            throw new IllegalArgumentException("Email cannot be empty");
        }
        return userRepository.findByUsername(username)
                .map(user -> {
                    Set<GrantedAuthority> authorities = new HashSet<>();
                    authorities.add(new SimpleGrantedAuthority(user.getRole().toString()));
                    return new org.springframework.security.core.userdetails.User(
                            user.getUsername(),
                            user.getPassword(),
                            authorities
                    );
                })
                .orElseThrow(() -> new UsernameNotFoundException("Failed to retrieve user:" + username));
    }

    public List<UserReadDto> findAll(){
        return userRepository.findAll().stream()
                .map(userReadMapper::map)
                .toList();
    }

    //TODO: method not finished
    /*@Transactional
    public boolean resetPassword(String username,String password){
        Optional<User> userOptional = userRepository.findByUsername(username);

        if(userOptional.isPresent()){
            User user = userOptional.get();
            user.setPassword(password);
            userRepository.save(user);

            if (!StringUtils.isEmpty(user.getUsername())) {
                String message = String.format(
                        "Hello, %s! \n" +
                                "You can reset your password clicking this link: http://localhost:8080/resetPassword",
                        user.getUsername()
                );
                mailService.send(user.getUsername(), "Activation code", message);
                return true;
            }
        }
        return false;
    }*/
}
