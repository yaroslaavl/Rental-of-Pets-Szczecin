package org.yaroslaavl.webappstarter.http.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

@Controller
@RequiredArgsConstructor
public class LocaleController {

    private final LocaleResolver localeResolver;

    @GetMapping("/changeLanguage")
    public String getLocalization(HttpServletRequest request, HttpServletResponse response, @RequestParam("lang") String lang){
        Locale locale = new Locale(lang);
        localeResolver.setLocale(request,response,locale);

        return "redirect:" + request.getHeader("Referer");
    }
}
