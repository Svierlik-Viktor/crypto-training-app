package com.cryptoapp.cryptotrainingapp;

import com.cryptoapp.cryptotrainingapp.vigenere.VigenereResponse;
import com.cryptoapp.cryptotrainingapp.vigenere.VigenereService;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class VigenereServiceTest {
    private final VigenereService service = new VigenereService();

    @Test
    public void testEncryptSimpleText() {
        VigenereResponse response = service.encrypt("HELLO", "KEY");
        assertEquals("RIJVS", response.getResult());
    }

    @Test
    public void testDecryptSimpleText() {
        VigenereResponse response = service.decrypt("RIJVS", "KEY");
        assertEquals("HELLO", response.getResult());
    }

    @Test
    public void testEncryptWithLowercaseAndSymbols() {
        VigenereResponse response = service.encrypt("hello world!", "KEY");
        assertEquals("RIJVSUYVJN", response.getResult()); // symbols and spaces are removed
    }

    @Test
    public void testDecryptWithLowercaseAndSymbols() {
        String ciphertext = "RIJVSUYVJN"; // corresponds to "HELLOWORLD"
        VigenereResponse response = service.decrypt(ciphertext, "KEY");
        assertEquals("HELLOWORLD", response.getResult());
    }

    @Test
    public void testEncryptAndDecryptInverse() {
        String original = "CRYPTOGRAPHY";
        String key = "VIGENERE";
        VigenereResponse encrypted = service.encrypt(original, key);
        VigenereResponse decrypted = service.decrypt(encrypted.getResult(), key);
        assertEquals(original.toUpperCase(), decrypted.getResult());
    }

    @Test
    public void testEncryptWithEmptyText() {
        VigenereResponse response = service.encrypt("", "KEY");
        assertEquals("", response.getResult());
    }

    @Test
    public void testEncryptWithEmptyKey() {
        assertThrows(IllegalArgumentException.class, () -> {
            service.encrypt("HELLO", "");
        });
    }
}