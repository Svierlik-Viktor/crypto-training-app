package com.cryptoapp.cryptotrainingapp;

import com.cryptoapp.cryptotrainingapp.CryptoTrainingAppApplication;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = CryptoTrainingAppApplication.class)
@AutoConfigureMockMvc
public class CaesarControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testEncrypt() throws Exception {
        Map<String, String> request = Map.of(
                "text", "abc",
                "shift", "3"
        );

        mockMvc.perform(post("/api/caesar/encrypt")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.inputText", is("abc")))
                .andExpect(jsonPath("$.shift", is(3)))
                .andExpect(jsonPath("$.resultText", is("def")))
                .andExpect(jsonPath("$.explanation", containsString("a → d")));
    }

    @Test
    public void testDecrypt() throws Exception {
        Map<String, String> request = Map.of(
                "text", "def",
                "shift", "3"
        );

        mockMvc.perform(post("/api/caesar/decrypt")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.inputText", is("def")))
                .andExpect(jsonPath("$.resultText", is("abc")))
                .andExpect(jsonPath("$.explanation", containsString("d → a")));
    }
    @Test
    void testInvalidShift() throws Exception {
        mockMvc.perform(post("/api/caesar/encrypt")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"text\":\"abc\",\"shift\":\"notANumber\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Невірний формат числа: For input string: \"notANumber\""));

    }
}