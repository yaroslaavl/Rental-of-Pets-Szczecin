package org.yaroslaavl.webappstarter.http.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.yaroslaavl.webappstarter.database.entity.User;
import org.yaroslaavl.webappstarter.dto.UserCreateEditDto;
import org.yaroslaavl.webappstarter.service.UserService;
import org.yaroslaavl.webappstarter.validation.EditAction;
import org.yaroslaavl.webappstarter.validation.ImageAction;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/user")
@Tag(name = "Settings Rest Controller")
public class SettingsRestController {

    PasswordEncoder passwordEncoder;

    private final UserService userService;

    @Operation(summary = "User settings next page")
    @GetMapping("/settings")
    public User settingsProfile(@AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        User user = userService.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return user;
    }

    @Operation(summary = "Update user first last name and birth date ")
    @PatchMapping("/settings")
    public ResponseEntity<?> update(@Validated({EditAction.class}) @RequestBody UserCreateEditDto user,
                                    BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            bindingResult
                    .getFieldErrors()
                    .forEach(f -> System.out.println(f.getField() + ": " + f.getDefaultMessage()));
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        userService.update(user.getId(), user);
        return ResponseEntity.noContent()
                .build();
    }

    @Operation(summary = "User settings next page")
    @GetMapping("/settings/account")
    public ResponseEntity<User> account(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails != null) {
            var username = userDetails.getUsername();
            var userOptional = userService.findByUsername(username);
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                return ResponseEntity.ok(user);
            }
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    @PatchMapping(value = "/settings/account", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateImage(
            @Validated({ImageAction.class}) @RequestPart("user") UserCreateEditDto userCreateEditDto,
            BindingResult bindingResult,
            @RequestPart("profilePicture") MultipartFile profilePicture,
            @AuthenticationPrincipal UserDetails userDetails) {

        var username = userDetails.getUsername();
        var userOptional = userService.findByUsername(username);

        if (userOptional.isPresent()) {
            UserCreateEditDto dto = new UserCreateEditDto(
                    userCreateEditDto.getId(),
                    userCreateEditDto.getUsername(),
                    userCreateEditDto.getPassword(),
                    userCreateEditDto.getBirthDate(),
                    userCreateEditDto.getFirstname(),
                    userCreateEditDto.getLastname(),
                    userCreateEditDto.getAddress(),
                    userCreateEditDto.getPesel(),
                    userCreateEditDto.getPhone(),
                    profilePicture,
                    userCreateEditDto.getRole(),
                    userCreateEditDto.getEmailVerified(),
                    userCreateEditDto.getEmailVerificationToken()
            );

            if (bindingResult.hasErrors()) {
                bindingResult.getFieldErrors().forEach(f -> System.out.println(f.getField() + ": " + f.getDefaultMessage()));
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            userService.update(dto.getId(), dto);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Resend account activation code")
    @PostMapping("/settings/account/resend-activation-code")
    public ResponseEntity<?> resendActivationCode(@AuthenticationPrincipal UserDetails userDetails){
        String username = userDetails.getUsername();
        boolean activationCodeResent = userService.resendActivationCode(username);
        if (activationCodeResent) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "Delete user account using password")
    @DeleteMapping("/settings/account/delete")
    public ResponseEntity<?> delete(@RequestBody UserCreateEditDto userCreateEditDto) {
        User optionalUser = userService.findUserById(userCreateEditDto.getId());

        if (optionalUser != null && passwordEncoder.matches(userCreateEditDto.getPassword(), optionalUser.getPassword())) {
            userService.delete(userCreateEditDto.getId());
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @Operation(summary = "Image download")
    @GetMapping("/images/{userId}")
    public ResponseEntity<Resource> downloadImage(@PathVariable Long userId) {
        Optional<byte[]> image = userService.findAvatar(userId);
        if (image.isPresent()) {
            byte[] imageData = image.get();
            ByteArrayResource resource = new ByteArrayResource(imageData);

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"user_" + userId + "\"")
                    .contentLength(imageData.length)
                    .body(resource);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
