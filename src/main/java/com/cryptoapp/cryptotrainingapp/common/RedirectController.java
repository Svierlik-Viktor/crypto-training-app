package com.cryptoapp.cryptotrainingapp.common;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RedirectController {
    @GetMapping("/")
    public String redirectToHome() {
        return "redirect:/home.html";
    }

}