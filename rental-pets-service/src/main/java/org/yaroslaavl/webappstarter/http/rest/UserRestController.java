package org.yaroslaavl.webappstarter.http.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import org.yaroslaavl.webappstarter.dto.UserCreateEditDto;
import org.yaroslaavl.webappstarter.dto.UserReadDto;
import org.yaroslaavl.webappstarter.service.UserService;
import org.yaroslaavl.webappstarter.validation.CreateAction;

import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
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
    public ResponseEntity<UserReadDto> create(@Validated({CreateAction.class}) @RequestBody UserCreateEditDto user,
                                              BindingResult bindingResult, UriComponentsBuilder uriComponentsBuilder) {
        if (bindingResult.hasErrors()) {
            bindingResult
                    .getFieldErrors()
                    .forEach(f -> System.out.println(f.getField() + ": " + f.getDefaultMessage()));
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else{
            var userReadDto = userService.create(user);
            return ResponseEntity.created(uriComponentsBuilder.replacePath("/api/users/create/{userId}")
                            .build(Map.of("userId",userReadDto.getId())))
                            .body(userReadDto);
        }
    }
}
