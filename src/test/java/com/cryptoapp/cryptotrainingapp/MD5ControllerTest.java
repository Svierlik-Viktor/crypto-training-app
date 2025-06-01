package com.cryptoapp.cryptotrainingapp;

import com.cryptoapp.cryptotrainingapp.md5.MD5Controller;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

public class MD5ControllerTest {
    private final MD5Controller controller = new MD5Controller();

    @Test
    public void testMd5HashCorrectness() {
        String input = "hello";
        String expectedHash = "5d41402abc4b2a76b9719d911017c592"; // MD5("hello")

        ResponseEntity<String> response = controller.hash(Map.of("text", input));

        assertEquals(200, response.getStatusCodeValue(), "Очікується статус 200 OK");
        String body = response.getBody();

        assertNotNull(body, "Тіло відповіді не повинно бути null");
        assertTrue(body.contains(expectedHash), "Тіло повинно містити правильний MD5-хеш");
        assertTrue(body.contains("<div class='explanation'>"), "Повинно містити HTML-пояснення");
        assertTrue(body.contains("Байтове представлення"), "Повинно містити байтове представлення");
    }

    @Test
    public void testMd5HashEmptyInput() {
        ResponseEntity<String> response = controller.hash(Map.of("text", ""));

        assertEquals(400, response.getStatusCodeValue(), "Очікується статус 400 для порожнього запиту");
        assertEquals("Порожній запит", response.getBody(), "Повідомлення про помилку повинно бути зрозумілим");
    }

    @Test
    public void testMd5HashMissingTextField() {
        ResponseEntity<String> response = controller.hash(Map.of());

        assertEquals(400, response.getStatusCodeValue(), "Очікується статус 400 при відсутності поля 'text'");
        assertEquals("Порожній запит", response.getBody(), "Повідомлення про помилку повинно бути коректним");
    }

}