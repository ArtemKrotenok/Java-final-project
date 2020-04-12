package com.gmail.artemkrotenok.mvc.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PrincipalController {

    @GetMapping()
    public String getStartPage() {
        return "redirect:/home";
    }

    @GetMapping("/login")
    public String getLoginPage() {
        return "login";
    }

    @GetMapping("/home")
    public String getHomePage() {
        return "home";
    }

}
