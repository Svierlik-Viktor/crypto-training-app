package com.cryptoapp.cryptotrainingapp.caesar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/caesar")
public class CaesarController {

    private final CaesarService caesarService;

    @Autowired
    public CaesarController(CaesarService caesarService) {
        this.caesarService = caesarService;
    }

    @PostMapping("/encrypt")
    public CaesarResult encrypt(@RequestBody Map<String, String> request) {
        String text = request.get("text");
        int shift = Integer.parseInt(request.get("shift"));
        return caesarService.encryptDetailed(text, shift);
    }

    @PostMapping("/decrypt")
    public CaesarResult decrypt(@RequestBody Map<String, String> request) {
        String text = request.get("text");
        int shift = Integer.parseInt(request.get("shift"));
        return caesarService.decryptDetailed(text, shift);
    }
}
