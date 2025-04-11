package com.cryptoapp.cryptotrainingapp;
import org.springframework.web.bind.annotation.*;

import java.security.GeneralSecurityException;
import java.util.Map;

@RestController
@RequestMapping("/api/aes")
public class AesController {

    @PostMapping("/encrypt")
    public Map<String, String> encrypt(@RequestBody Map<String, String> payload) throws GeneralSecurityException {
        String key = payload.get("key");
        String iv = payload.get("iv");
        String plaintext = payload.get("plaintext");

        String encrypted = AesUtil.encrypt(key, iv, plaintext);
        return Map.of("ciphertext", encrypted);
    }

    @PostMapping("/decrypt")
    public Map<String, String> decrypt(@RequestBody Map<String, String> payload) throws GeneralSecurityException {
        String key = payload.get("key");
        String iv = payload.get("iv");
        String ciphertext = payload.get("ciphertext");

        String decrypted = AesUtil.decrypt(key, iv, ciphertext);
        return Map.of("plaintext", decrypted);
    }
}

