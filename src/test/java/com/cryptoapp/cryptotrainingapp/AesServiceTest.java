package com.cryptoapp.cryptotrainingapp;

import com.cryptoapp.cryptotrainingapp.aes.AesService;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.security.GeneralSecurityException;
import java.security.Security;

import static org.junit.jupiter.api.Assertions.*;

public class AesServiceTest {
    @BeforeAll
    public static void setUp() {
        if (Security.getProvider("BC") == null) {
            Security.addProvider(new BouncyCastleProvider());
        }
    }

    @Test
    public void testEncryptAndDecryptSuccess() throws GeneralSecurityException {
        String key = "1234567890abcdef";
        String iv = "abcdef1234567890";
        String plaintext = "Hello AES";

        String encrypted = AesService.encrypt(key, iv, plaintext);
        String decrypted = AesService.decrypt(key, iv, encrypted);

        assertEquals(plaintext, decrypted);
    }

    @Test
    public void testEncryptWithInvalidKeyLength() {
        String key = "shortkey";
        String iv = "abcdef1234567890";
        String plaintext = "Test";

        assertThrows(GeneralSecurityException.class, () -> {
            AesService.encrypt(key, iv, plaintext);
        });
    }

    @Test
    public void testDecryptWithInvalidIV() {
        String key = "1234567890abcdef";
        String iv = "short";
        String ciphertext = "abcd1234";

        assertThrows(GeneralSecurityException.class, () -> {
            AesService.decrypt(key, iv, ciphertext);
        });
    }

    @Test
    public void testDecryptWithWrongKey() throws GeneralSecurityException {
        String key = "1234567890abcdef";
        String wrongKey = "fedcba0987654321";
        String iv = "abcdef1234567890";
        String plaintext = "Sensitive Data";

        String encrypted = AesService.encrypt(key, iv, plaintext);

        assertThrows(GeneralSecurityException.class, () -> {
            AesService.decrypt(wrongKey, iv, encrypted);
        });
    }

}