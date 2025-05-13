package com.cryptoapp.cryptotrainingapp;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

@RestController
@RequestMapping("/api/md5")
public class MD5Controller {

    @PostMapping("/hash")
    public ResponseEntity<String> hash(@RequestBody Map<String, String> request) {
        String input = request.get("text");
        if (input == null || input.isEmpty()) {
            return ResponseEntity.badRequest().body("Порожній запит");
        }

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");

            // Отримання байтів тексту
            byte[] inputBytes = input.getBytes();
            byte[] digest = md.digest(inputBytes);

            // Перетворення в hex
            StringBuilder hexString = new StringBuilder();
            for (byte b : digest) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            StringBuilder explanation = new StringBuilder();
            explanation.append("<div class='explanation'>");
            explanation.append("<p><b>Вхідне повідомлення:</b> ").append(input).append("</p>");

            explanation.append("<p><b>Байтове представлення (UTF-8):</b></p><pre>");
            for (byte b : inputBytes) {
                explanation.append(String.format("%02x ", b));
            }
            explanation.append("</pre>");

            explanation.append("<p><b>Результат хешування (MD5, hex):</b></p>");
            explanation.append("<div class='mono'>").append(hexString).append("</div>");
            explanation.append("</div>");

            return ResponseEntity.ok(explanation.toString());

        } catch (NoSuchAlgorithmException e) {
            return ResponseEntity.internalServerError().body("Помилка алгоритму MD5");
        }
    }
}
