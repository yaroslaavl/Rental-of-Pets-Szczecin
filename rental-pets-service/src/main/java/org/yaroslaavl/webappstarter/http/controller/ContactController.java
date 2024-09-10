package org.yaroslaavl.webappstarter.http.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Tag(name = "Contact Controller ")
public class ContactController {

    @Operation(summary= "Contact info")
    @GetMapping("/company-info")
    public String companyInformation(){
        return "user/map/mapAndCompanyInfo";
    }

}
