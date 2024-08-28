package org.yaroslaavl.webappstarter.http.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/user")
@Tag(name = "Settings Controller")
public class SettingsController {

    @Autowired
    PasswordEncoder passwordEncoder;
    private final UserService userService;

    @Operation(summary = "User settings form")
    @GetMapping("/settings")
    public String settingsProfile(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        var username = userDetails.getUsername();
        var userOptional = userService.findByUsername(username);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            model.addAttribute("user", user);
            return "user/settings/settings";
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Update user")
    @PostMapping("/settings")
    public String update(@ModelAttribute @Validated({EditAction.class}) UserCreateEditDto user,
                         BindingResult bindingResult,
                         Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("user", user);
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "user/settings/settings";
        }
        userService.update(user.getId(), user);
        return "redirect:/user/settings";
    }

    @GetMapping("/settings/account")
    public String account(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails != null) {
            var username = userDetails.getUsername();
            var userOptional = userService.findByUsername(username);
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                model.addAttribute("user", user);
                return "user/settings/account";
            }
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/settings/account")
    public String updateImage(@ModelAttribute @Validated({ImageAction.class}) UserCreateEditDto user,
                              BindingResult bindingResult,
                              @RequestParam("profilePicture") MultipartFile profilePicture,
                              Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("user", user);
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "user/settings/account";
        }
        userService.update(user.getId(),user);
        return "redirect:/user/settings/account";
    }

    @PostMapping("/settings/account/resend-activation-code")
    public String resendActivationCode(@AuthenticationPrincipal UserDetails userDetails){
        String username = userDetails.getUsername();
        boolean activationCodeResent = userService.resendActivationCode(username);
        if (activationCodeResent) {
            return "redirect:/user/settings/account";
        } else {
            return "redirect:/login";
        }
    }

    @PostMapping("/settings/account/delete")
    public String delete(@ModelAttribute UserCreateEditDto userCreateEditDto) {
        User optionalUser = userService.findUserById(userCreateEditDto.getId());

            if (passwordEncoder.matches(userCreateEditDto.getPassword(), optionalUser.getPassword())) {
                userService.delete(userCreateEditDto.getId());
                return "redirect:/login";
            }

        return "redirect:/user/settings/account";
    }

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
