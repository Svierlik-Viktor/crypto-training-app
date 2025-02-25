package com.cryptoapp.cryptotrainingapp;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CryptoController {

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("message", "Welcome to Crypto Training App!");
        return "home";
    }
}
