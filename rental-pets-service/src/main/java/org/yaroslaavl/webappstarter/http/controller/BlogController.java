package org.yaroslaavl.webappstarter.http.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class BlogController {

    @GetMapping("/blog")
    public String forbidden() {
        return "error/503";
    }
}
