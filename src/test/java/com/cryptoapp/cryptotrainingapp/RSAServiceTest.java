package com.cryptoapp.cryptotrainingapp;

import com.cryptoapp.cryptotrainingapp.rsa.RSAResult;
import com.cryptoapp.cryptotrainingapp.rsa.RSAService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RSAServiceTest {
    private RSAService rsaService;

    @BeforeEach
    public void setUp() throws Exception {
        rsaService = new RSAService();
        rsaService.setup(); // додає BouncyCastle
        rsaService.generateKeys(); // генерація ключів для тестів
    }

    @Test
    public void testEncryptDecrypt() throws Exception {
        String original = "Test message for RSA encryption";

        RSAResult encrypted = rsaService.encryptWithExplanation(original);
        assertNotNull(encrypted.getEncryptedBase64(), "Зашифрований текст не повинен бути null");
        assertTrue(encrypted.getHtmlFormatted().contains("Кроки шифрування RSA"), "HTML повинен містити пояснення");

        RSAResult decrypted = rsaService.decryptWithExplanation(encrypted.getEncryptedBase64());
        assertEquals(original, decrypted.getOutputText(), "Розшифрований текст повинен збігатися з оригіналом");
        assertTrue(decrypted.getHtmlFormatted().contains("Кроки розшифрування RSA"), "HTML повинен містити пояснення");
    }

    @Test
    public void testDecryptWithInvalidData() {
        String invalidEncryptedBase64 = "abcd1234"; // формально валідний Base64, але недійсний для RSA
        Exception exception = assertThrows(Exception.class, () -> {
            rsaService.decryptWithExplanation(invalidEncryptedBase64);
        });

        String message = exception.getMessage();
        assertNotNull(message, "Очікується повідомлення про помилку");
        assertTrue(message.contains("data") || message.contains("pad") || message.contains("decrypt"),
                "Очікується помилка при спробі розшифрування (а не при декодуванні Base64)");
    }

        @Test
    public void testEncryptWithoutKeys() {
        RSAService service = new RSAService();
        service.setup(); // без generateKeys

        Exception exception = assertThrows(IllegalStateException.class, () -> {
            service.encryptWithExplanation("Test");
        });

        assertEquals("Ключі не згенеровані", exception.getMessage());
    }

    @Test
    public void testDecryptWithoutKeys() {
        RSAService service = new RSAService();
        service.setup(); // без generateKeys

        Exception exception = assertThrows(IllegalStateException.class, () -> {
            service.decryptWithExplanation("abcdef");
        });

        assertEquals("Ключі не згенеровані", exception.getMessage());
    }

}