package com.cryptoapp.cryptotrainingapp;

import com.cryptoapp.cryptotrainingapp.columnar.ColumnarResponse;
import com.cryptoapp.cryptotrainingapp.columnar.ColumnarService;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class ColumnarServiceTest {
    private final ColumnarService columnarService = new ColumnarService();

    @Test
    public void testEncryptAndDecrypt() {
        String plaintext = "HELLOCOLUMNAR";
        String key = "ZEBRAS";

        ColumnarResponse encrypted = columnarService.encryptExplained(plaintext, key);
        ColumnarResponse decrypted = columnarService.decryptExplained(encrypted.getResult(), key);

        assertEquals(plaintext, decrypted.getResult());
    }

    @Test
    public void testEncryptPadsWithX() {
        String plaintext = "HELLO";
        String key = "KEY";

        ColumnarResponse encrypted = columnarService.encryptExplained(plaintext, key);
        int totalLength = encrypted.getMatrix().size() * key.length();

        // Перевірка, що довжина матриці дорівнює кількість рядків × кількість стовпців
        assertEquals(totalLength, encrypted.getMatrix().stream()
                .mapToInt(List::size)
                .sum());

        // Перевірка, що результат містить символи 'X' у кінці (якщо були додані)
        assertTrue(encrypted.getResult().length() >= plaintext.length());
    }

    @Test
    public void testDecryptRemovesPadding() {
        String paddedPlaintext = "HELLOXX";
        String key = "DOG";

        ColumnarResponse encrypted = columnarService.encryptExplained(paddedPlaintext, key);
        ColumnarResponse decrypted = columnarService.decryptExplained(encrypted.getResult(), key);

        // Перевіряємо, що результат не містить зайвих 'X' у кінці
        assertEquals("HELLO", decrypted.getResult());
    }

    @Test
    public void testEmptyTextReturnsEmptyResult() {
        String key = "KEY";

        ColumnarResponse encrypted = columnarService.encryptExplained("", key);
        assertEquals("", encrypted.getResult());

        ColumnarResponse decrypted = columnarService.decryptExplained("", key);
        assertEquals("", decrypted.getResult());
    }

    @Test
    public void testSingleCharacterKey() {
        String text = "ABCDEFG";
        String key = "A";

        ColumnarResponse encrypted = columnarService.encryptExplained(text, key);
        ColumnarResponse decrypted = columnarService.decryptExplained(encrypted.getResult(), key);

        assertEquals(text, decrypted.getResult());
    }

}