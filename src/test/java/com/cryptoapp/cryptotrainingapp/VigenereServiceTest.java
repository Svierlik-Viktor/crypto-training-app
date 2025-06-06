package com.cryptoapp.cryptotrainingapp;

import com.cryptoapp.cryptotrainingapp.vigenere.VigenereResult;
import com.cryptoapp.cryptotrainingapp.vigenere.VigenereService;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class VigenereServiceTest {
    private final VigenereService service = new VigenereService();

    @Test
    public void testEncryptSimpleText() {
        VigenereResult response = service.encrypt("HELLO", "KEY");
        assertEquals("RIJVS", response.getResult());
    }

    @Test
    public void testDecryptSimpleText() {
        VigenereResult response = service.decrypt("RIJVS", "KEY");
        assertEquals("HELLO", response.getResult());
    }

    @Test
    public void testEncryptWithLowercaseAndSymbols() {
        VigenereResult response = service.encrypt("hello world!", "KEY");
        assertEquals("RIJVSUYVJN", response.getResult()); // symbols and spaces are removed
    }

    @Test
    public void testDecryptWithLowercaseAndSymbols() {
        String ciphertext = "RIJVSUYVJN"; // corresponds to "HELLOWORLD"
        VigenereResult response = service.decrypt(ciphertext, "KEY");
        assertEquals("HELLOWORLD", response.getResult());
    }

    @Test
    public void testEncryptAndDecryptInverse() {
        String original = "CRYPTOGRAPHY";
        String key = "VIGENERE";
        VigenereResult encrypted = service.encrypt(original, key);
        VigenereResult decrypted = service.decrypt(encrypted.getResult(), key);
        assertEquals(original.toUpperCase(), decrypted.getResult());
    }

    @Test
    public void testEncryptWithEmptyText() {
        VigenereResult response = service.encrypt("", "KEY");
        assertEquals("", response.getResult());
    }

    @Test
    public void testEncryptWithEmptyKey() {
        assertThrows(IllegalArgumentException.class, () -> {
            service.encrypt("HELLO", "");
        });
    }
}