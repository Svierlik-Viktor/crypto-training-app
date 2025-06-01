package com.cryptoapp.cryptotrainingapp;

import com.cryptoapp.cryptotrainingapp.caesar.CaesarResult;
import com.cryptoapp.cryptotrainingapp.caesar.CaesarService;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CaesarServiceTest {
    private final CaesarService caesarService = new CaesarService();

    @Test
    public void testEncryptSimpleText() {
        CaesarResult result = caesarService.encryptDetailed("abc", 3);
        assertEquals("def", result.getResultText());
    }

    @Test
    public void testEncryptWithWrapAround() {
        CaesarResult result = caesarService.encryptDetailed("xyz", 3);
        assertEquals("abc", result.getResultText());
    }

    @Test
    public void testEncryptWithMixedCase() {
        CaesarResult result = caesarService.encryptDetailed("AbC", 1);
        assertEquals("BcD", result.getResultText());
    }

    @Test
    public void testEncryptWithNonAlphabeticCharacters() {
        CaesarResult result = caesarService.encryptDetailed("a!b c.", 2);
        assertEquals("c!d e.", result.getResultText());
    }

    @Test
    public void testDecryptSimpleText() {
        CaesarResult result = caesarService.decryptDetailed("def", 3);
        assertEquals("abc", result.getResultText());
    }

    @Test
    public void testDecryptWithWrapAround() {
        CaesarResult result = caesarService.decryptDetailed("abc", 3);
        assertEquals("xyz", result.getResultText());
    }

    @Test
    public void testEncryptAndDecryptInverse() {
        String original = "Crypto123";
        int shift = 5;
        CaesarResult encrypted = caesarService.encryptDetailed(original, shift);
        CaesarResult decrypted = caesarService.decryptDetailed(encrypted.getResultText(), shift);
        assertEquals(original, decrypted.getResultText());
    }

}