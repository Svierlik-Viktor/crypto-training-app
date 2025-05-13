package com.cryptoapp.cryptotrainingapp;

import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

@CrossOrigin
@RestController
@RequestMapping("/api/crypto")
public class HashController {

    @PostMapping("/sha256")
    public String hashSha256(@RequestBody String input) throws Exception {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");

        byte[] inputBytes = input.getBytes(StandardCharsets.UTF_8);
        byte[] hashBytes = digest.digest(inputBytes);

        StringBuilder hexHash = new StringBuilder();
        for (byte b : hashBytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) hexHash.append('0');
            hexHash.append(hex);
        }

        StringBuilder explanation = new StringBuilder();
        explanation.append("<div class='explanation'>");

        // Вхідний текст
        explanation.append("<p><b>Вхідне повідомлення:</b> ").append(input).append("</p>");

        // Байтове представлення
        explanation.append("<p><b>Байтове представлення (UTF-8):</b></p><pre>");
        for (byte b : inputBytes) {
            explanation.append(String.format("%02x ", b));
        }
        explanation.append("</pre>");

        // Хеш результат
        explanation.append("<p><b>Результат хешування (SHA-256, hex):</b></p>");
        explanation.append("<div class='mono'>").append(hexHash).append("</div>");

        explanation.append("</div>");
        return explanation.toString();
    }
}
