package com.cryptoapp.cryptotrainingapp.tripledes;

import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

@Service
public class TripleDesService {

    private static final String TRANSFORMATION = "DESede/ECB/PKCS5Padding";

    public String encrypt(String plaintext, String keyString) throws Exception {
        byte[] keyBytes = getValidKeyBytes(keyString);
        SecretKey secretKey = new SecretKeySpec(keyBytes, "DESede");

        Cipher cipher = Cipher.getInstance(TRANSFORMATION);

        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encrypted = cipher.doFinal(plaintext.getBytes());

        // Prepare explanation
        StringBuilder explanation = new StringBuilder();
        explanation.append("<div class='step'><strong style='color:#05b905;'>[КРОК 1] Вхідні дані</strong><br>")
                .append("Текст: <code>").append(plaintext).append("</code><br>")
                .append("Ключ (24 символи): <code>").append(keyString).append("</code></div>");

        explanation.append("<div class='step'><strong style='color:#2980b9;'>[КРОК 2] Triple DES (EDE)</strong><br>")
                .append("Виконується шифрування за схемою Encrypt → Decrypt → Encrypt (EDE) з використанням ключів:<br>")
                .append("<ul>")
                .append("<li><span style='color:#05b905;'>K1</span>: ").append(new String(getKeyPart(keyBytes, 0))).append("</li>")
                .append("<li><span style='color:#2980b9;'>K2</span>: ").append(new String(getKeyPart(keyBytes, 8))).append("</li>")
                .append("<li><span style='color:#c40ec4;'>K3</span>: ").append(new String(getKeyPart(keyBytes, 16))).append("</li>")
                .append("</ul></div>");

        explanation.append("<div class='step'><strong style='color:#c40ec4;'>[КРОК 3] Результат шифрування</strong><br>")
                .append("Base64: <code>").append(Base64.getEncoder().encodeToString(encrypted)).append("</code></div>");

        return explanation.toString();
    }

    public String decrypt(String ciphertext, String keyString) throws Exception {
        byte[] keyBytes = getValidKeyBytes(keyString);
        SecretKey secretKey = new SecretKeySpec(keyBytes, "DESede");

        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decoded = Base64.getDecoder().decode(ciphertext);
        byte[] decrypted = cipher.doFinal(decoded);
        String result = new String(decrypted);

        // Prepare explanation
        StringBuilder explanation = new StringBuilder();
        explanation.append("<div class='step'><strong style='color:#05b905;'>[КРОК 1] Вхідні дані</strong><br>")
                .append("Base64-шифр: <code>").append(ciphertext).append("</code><br>")
                .append("Ключ (24 символи): <code>").append(keyString).append("</code></div>");

        explanation.append("<div class='step'><strong style='color:#2980b9;'>[КРОК 2] Triple DES (EDE)</strong><br>")
                .append("Виконується розшифрування за схемою Decrypt → Encrypt → Decrypt (DED) з використанням ключів:<br>")
                .append("<ul>")
                .append("<li><span style='color:#05b905;'>K1</span>: ").append(new String(getKeyPart(keyBytes, 0))).append("</li>")
                .append("<li><span style='color:#2980b9;'>K2</span>: ").append(new String(getKeyPart(keyBytes, 8))).append("</li>")
                .append("<li><span style='color:#c40ec4;'>K3</span>: ").append(new String(getKeyPart(keyBytes, 16))).append("</li>")
                .append("</ul></div>");

        explanation.append("<div class='step'><strong style='color:#c40ec4;'>[КРОК 3] Результат розшифрування</strong><br>")
                .append("Розшифрований текст: <code>").append(result).append("</code></div>");

        return explanation.toString();
    }

    private byte[] getValidKeyBytes(String keyString) throws Exception {
        byte[] keyBytes = keyString.getBytes("UTF-8");
        if (keyBytes.length != 24) {
            throw new IllegalArgumentException("Ключ має бути рівно 24 байти (24 символи).");
        }
        return keyBytes;
    }

    private byte[] getKeyPart(byte[] fullKey, int startIndex) {
        byte[] part = new byte[8];
        System.arraycopy(fullKey, startIndex, part, 0, 8);
        return part;
    }
}
