package org.yaroslaavl.webappstarter.http.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.yaroslaavl.webappstarter.service.UserService;

@Controller
@RequiredArgsConstructor
public class ActivationController {

    private final UserService userService;

    @GetMapping("/activate")
    public String activateAccount(@RequestParam("token") String activationToken, Model model) {
        boolean activated = userService.activation(activationToken);
        model.addAttribute("activated",activated);
        return "activation/activation";
    }
}