package com.cryptoapp.cryptotrainingapp.rsa;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/rsa")
public class RSAController {

    private final RSAService rsaService;

    public RSAController(RSAService rsaService) {
        this.rsaService = rsaService;
    }

    @GetMapping("/generate")
    public ResponseEntity<Map<String, String>> generateKeys() throws Exception {
        rsaService.generateKeys();
        Map<String, String> keys = new HashMap<>();
        keys.put("publicKey", rsaService.getPublicKeyAsString());
        keys.put("privateKey", rsaService.getPrivateKeyAsString());
        return ResponseEntity.ok(keys);
    }

    @PostMapping("/encrypt")
    public ResponseEntity<String> encrypt(@RequestBody Map<String, String> request) {
        try {
            String text = request.get("text");
            RSAResultExplanation explanation = rsaService.encryptWithExplanation(text);
            return ResponseEntity.ok(explanation.getHtmlFormatted());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Помилка шифрування: " + e.getMessage());
        }
    }

    @PostMapping("/decrypt")
    public ResponseEntity<String> decrypt(@RequestBody Map<String, String> request) {
        try {
            String encryptedText = request.get("text");
            RSAResultExplanation explanation = rsaService.decryptWithExplanation(encryptedText);
            return ResponseEntity.ok(explanation.getHtmlFormatted());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Помилка розшифрування: " + e.getMessage());
        }
    }
}
