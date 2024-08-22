package org.yaroslaavl.webappstarter.http.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Tag(name = "Privacy Page Controller")
public class PrivacyPageController {

    @Operation(summary = "Privacy policy")
    @GetMapping("/privacy-company")
    public String privacy(){
        return "privacy/privacy-company";
    }
}
