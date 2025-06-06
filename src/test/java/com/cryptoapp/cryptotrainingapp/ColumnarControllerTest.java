package com.cryptoapp.cryptotrainingapp;

import com.cryptoapp.cryptotrainingapp.columnar.ColumnarResult;
import com.cryptoapp.cryptotrainingapp.columnar.ColumnarService;
import com.cryptoapp.cryptotrainingapp.common.TextWithKeyRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = CryptoTrainingAppApplication.class)
@AutoConfigureMockMvc
public class ColumnarControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testEncryptEndpoint() throws Exception {
        TextWithKeyRequest request = new TextWithKeyRequest();
        request.setText("HELLO WORLD");
        request.setKey("ZEBRAS");

        mockMvc.perform(post("/api/columnar/encrypt")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result", not(emptyOrNullString())))
                .andExpect(jsonPath("$.key", hasSize(6)))
                .andExpect(jsonPath("$.order", hasSize(6)))
                .andExpect(jsonPath("$.matrix", not(empty())));
    }

    @Test
    public void testDecryptEndpoint() throws Exception {
        // Зашифруємо текст за допомогою сервісу
        ColumnarService service = new ColumnarService();
        ColumnarResult encrypted = service.encryptExplained("HELLO WORLD", "ZEBRAS");

        TextWithKeyRequest request = new TextWithKeyRequest();
        request.setText(encrypted.getResult());
        request.setKey("ZEBRAS");

        mockMvc.perform(post("/api/columnar/decrypt")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result", containsString("HELLO")))
                .andExpect(jsonPath("$.key", hasSize(6)))
                .andExpect(jsonPath("$.order", hasSize(6)))
                .andExpect(jsonPath("$.matrix", not(empty())));
    }

}