package com.cryptoapp.cryptotrainingapp;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller public class PageController {

    @GetMapping("/home")
    public String home() {
        return "home";
    }

    @GetMapping("/theory")
    public String theory() {
        return "theory";
    }

    @GetMapping("/theory-aes")
    public String theoryAES() {
        return "theory-aes";
    }

    @GetMapping("/theory-rsa")
    public String theoryRSA() {
        return "theory-rsa";
    }

    @GetMapping("/theory-sha256")
    public String theorySHA() {
        return "theory-sha256";
    }

    @GetMapping("/theory-caesar")
    public String theoryCaesar() {
        return "theory-caesar";
    }

    @GetMapping("/theory-vigenere")
    public String theoryVigenere() {
        return "theory-vigenere";
    }

    @GetMapping("/theory-columnar")
    public String theoryColumnar() {
        return "theory-columnar";
    }

    @GetMapping("/theory-tripledes")
    public String theoryTripledes() {
        return "theory-tripledes";
    }

    @GetMapping("/theory-md5")
    public String theoryMd5() {
        return "theory-md5";
    }

    @GetMapping("/theory-ecdsa")
    public String theoryEcdsa() {
        return "theory-ecdsa";
    }

    @GetMapping("/practice")
    public String practice() {
        return "practice";
    }

    @GetMapping("/practice-aes")
    public String practiceAES() {
        return "practice-aes";
    }

    @GetMapping("/practice-rsa")
    public String practiceRSA() {
        return "practice-rsa";
    }

    @GetMapping("/practice-sha256")
    public String practiceSHA() {
        return "practice-sha256";
    }

    @GetMapping("/practice-vigenere")
    public String practiceVigenere() {
        return "practice-vigenere";
    }

    @GetMapping("/practice-caesar")
    public String practiceCaesar() {
        return "practice-caesar";
    }

    @GetMapping("/practice-columnar")
    public String practiceColumnar() {
        return "practice-columnar";
    }

    @GetMapping("/practice-tripledes")
    public String practiceTripledes() {
        return "practice-tripledes";
    }

    @GetMapping("/practice-ecdsa")
    public String practiceEcdsa() {
        return "practice-ecdsa";
    }

    @GetMapping("/practice-md5")
    public String practiceMd5() {
        return "practice-md5";
    }

    @GetMapping("/testing")
    public String testing() {
        return "testing";
    }

    @GetMapping("/testing-aes")
    public String testingAES() {
        return "testing-aes";
    }

    @GetMapping("/testing-rsa")
    public String testingRSA() {
        return "testing-rsa";
    }

    @GetMapping("/testing-sha256")
    public String testingSHA() {
        return "testing-sha256";
    }

    @GetMapping("/testing-vigenere")
    public String testingVigenere() {
        return "testing-vigenere";
    }

    @GetMapping("/testing-caesar")
    public String testingCaesar() {
        return "testing-caesar";
    }

    @GetMapping("/testing-columnar")
    public String testingColumnar() {
        return "testing-columnar";
    }

    @GetMapping("/testing-tripledes")
    public String testingTripledes() {
        return "testing-tripledes";
    }

    @GetMapping("/testing-ecdsa")
    public String testingEcdsa() {
        return "testing-ecdsa";
    }

    @GetMapping("/testing-md5")
    public String testingMd5() {
        return "testing-md5";
    }
    @GetMapping("/history")
    public String history() {
        return "history";
    }
}