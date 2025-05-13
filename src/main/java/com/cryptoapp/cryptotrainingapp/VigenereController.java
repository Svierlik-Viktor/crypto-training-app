package com.cryptoapp.cryptotrainingapp;

import org.springframework.beans.factory.annotation.Autowired; import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/vigenere")
public class VigenereController {

    @Autowired
    private VigenereService vigenereService;

    @PostMapping("/encrypt")
    public VigenereResponse encrypt(@RequestBody Map<String, String> request) {
        return vigenereService.encrypt(request.get("text"), request.get("key"));
    }

    @PostMapping("/decrypt")
    public VigenereResponse decrypt(@RequestBody Map<String, String> request) {
        return vigenereService.decrypt(request.get("text"), request.get("key"));
    }
}
