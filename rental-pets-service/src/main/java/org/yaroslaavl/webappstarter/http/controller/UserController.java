package org.yaroslaavl.webappstarter.http.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.yaroslaavl.webappstarter.dto.UserCreateEditDto;
import org.yaroslaavl.webappstarter.service.UserService;
import org.yaroslaavl.webappstarter.validation.CreateAction;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/users")
@Tag(name = "User Controller")
public class UserController {

    private final UserService userService;

    @Operation(summary = "User registration form", description = "Displays the user registration form")
    @GetMapping("/registration")
    public String registration(Model model, @ModelAttribute("user") UserCreateEditDto user) {
        model.addAttribute("user", user);
        return "user/registration";
    }

    @Operation(summary = "Create user", description = "Creates a new user")
    @PostMapping("/registration")
    public String create(@ModelAttribute("user") @Validated({CreateAction.class}) UserCreateEditDto user,
                         BindingResult bindingResult,
                         Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("user", user);
            model.addAttribute("errors",bindingResult.getAllErrors());

            return "user/registration";
        }
        userService.create(user);
        return "redirect:/login";
    }

}
