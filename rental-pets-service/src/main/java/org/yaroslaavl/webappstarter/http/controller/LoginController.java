package org.yaroslaavl.webappstarter.http.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
@Tag(name = "Login Controller")
public class LoginController {

    @Operation(summary = "Login page")
    @GetMapping("/login")
    public String loginPage() {
        return "user/login";
    }




}
