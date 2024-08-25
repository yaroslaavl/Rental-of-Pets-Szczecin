package org.yaroslaavl.webappstarter.http.controller.forbidden;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class ForbiddenController {

    @GetMapping("/forbidden-error")
    public String forbidden() {
        return "user/errorAuthentication/Forbidden";
    }
}
