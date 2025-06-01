package com.cryptoapp.cryptotrainingapp;

import com.cryptoapp.cryptotrainingapp.tripledes.TripleDesService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TripleDesServiceTest {
    private final TripleDesService tripleDesService = new TripleDesService();

    @Test
    void testEncryptAndDecryptSuccess() throws Exception {
        String text = "HelloTripleDES";
        String key = "1234567890ABCDEF12345678"; // 24 символи

        String encryptedHtml = tripleDesService.encrypt(text, key);
        assertNotNull(encryptedHtml);
        assertTrue(encryptedHtml.contains("Base64"));

        String base64Encrypted = extractBase64(encryptedHtml);
        assertNotNull(base64Encrypted);

        String decryptedHtml = tripleDesService.decrypt(base64Encrypted, key);
        assertNotNull(decryptedHtml);
        assertTrue(decryptedHtml.contains("Розшифрований текст"));
        assertTrue(decryptedHtml.contains(text));
    }

    @Test
    void testInvalidKeyLengthThrowsException() {
        String text = "Hello";
        String shortKey = "TooShortKey"; // < 24 символи

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            tripleDesService.encrypt(text, shortKey);
        });

        assertEquals("Ключ має бути рівно 24 байти (24 символи).", exception.getMessage());
    }

    @Test
    void testEmptyTextEncrypt() throws Exception {
        String key = "1234567890ABCDEF12345678"; // Valid key
        String encrypted = tripleDesService.encrypt("", key);
        assertNotNull(encrypted);
        assertTrue(encrypted.contains("Base64"));
    }

    @Test
    void testEmptyTextDecrypt() throws Exception {
        String key = "1234567890ABCDEF12345678";
        String encrypted = tripleDesService.encrypt("", key);
        String base64Encrypted = extractBase64(encrypted);

        String decryptedHtml = tripleDesService.decrypt(base64Encrypted, key);
        assertTrue(decryptedHtml.contains("Розшифрований текст"));
        assertTrue(decryptedHtml.contains("</code></div>")); // порожній текст
    }

    // Допоміжний метод для виділення Base64 з HTML
    private String extractBase64(String html) {
        String marker = "Base64: <code>";
        int start = html.indexOf(marker);
        if (start == -1) return null;
        int end = html.indexOf("</code>", start);
        return html.substring(start + marker.length(), end).trim();
    }

}