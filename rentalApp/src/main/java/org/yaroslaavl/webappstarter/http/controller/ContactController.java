package org.yaroslaavl.webappstarter.http.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ContactController {

    @GetMapping("/company-info")
    public String companyInformation(){
        return "user/map/mapAndCompanyInfo";
    }

}
