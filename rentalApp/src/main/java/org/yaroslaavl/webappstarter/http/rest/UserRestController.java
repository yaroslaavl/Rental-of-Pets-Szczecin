package org.yaroslaavl.webappstarter.http.rest;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.yaroslaavl.webappstarter.dto.UserCreateEditDto;
import org.yaroslaavl.webappstarter.service.UserService;
import org.yaroslaavl.webappstarter.validation.CreateAction;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/users")
@Tag(name = "User Rest Controller")
public class UserRestController {

    private final UserService userService;

    @Operation(summary = "User registration form", description = "Displays the user registration form")
    @GetMapping("/create")
    public ResponseEntity<UserCreateEditDto> registrationForm(@RequestBody UserCreateEditDto user) {
        return ResponseEntity.ok(user);
    }

    @Operation(summary = "Create user", description = "Creates a new user")
    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> create(@Validated({CreateAction.class}) @RequestBody UserCreateEditDto user,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }
        userService.create(user);
        return ResponseEntity.ok("User created successfully");
    }
}
