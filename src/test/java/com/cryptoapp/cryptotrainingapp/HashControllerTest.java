package com.cryptoapp.cryptotrainingapp;

import com.cryptoapp.cryptotrainingapp.sha256.HashController;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class HashControllerTest {
    private final HashController controller = new HashController();

    @Test
    public void testSha256HashCorrectness() throws Exception {
        String input = "hello";
        String expectedHash = "2cf24dba5fb0a30e26e83b2ac5b9e29e1b161e5c1fa7425e73043362938b9824";

        String resultHtml = controller.hashSha256(input);

        assertTrue(resultHtml.contains(expectedHash), "Результат повинен містити правильний SHA-256 хеш");
        assertTrue(resultHtml.contains("<div class='explanation'>"), "Результат повинен містити пояснення в HTML");
        assertTrue(resultHtml.contains("<b>Вхідне повідомлення:</b>"), "Повинен бути заголовок вхідного повідомлення");
        assertTrue(resultHtml.contains("Байтове представлення (UTF-8):"), "Повинно бути представлення в байтах");
        assertTrue(resultHtml.contains("Результат хешування"), "Повинно бути зазначено, що це хеш");
    }

    @Test
    public void testSha256HashOfEmptyString() throws Exception {
        String input = "";
        String expectedHash = "e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855"; // SHA-256 of ""

        String resultHtml = controller.hashSha256(input);

        assertTrue(resultHtml.contains(expectedHash), "Результат повинен містити SHA-256 хеш порожнього рядка");
    }

}