package com.cryptoapp.cryptotrainingapp;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = CryptoTrainingAppApplication.class)
@AutoConfigureMockMvc
public class VigenereControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testEncrypt() throws Exception {
        mockMvc.perform(post("/api/vigenere/encrypt")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"text\":\"HELLO\",\"key\":\"KEY\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result", notNullValue()))
                .andExpect(jsonPath("$.explanation", containsString("H")));
    }

    @Test
    public void testDecrypt() throws Exception {
        mockMvc.perform(post("/api/vigenere/decrypt")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"text\":\"RIJVS\",\"key\":\"KEY\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result", notNullValue()))
                .andExpect(jsonPath("$.explanation", containsString("R")));
    }
    @Test
    public void testMissingKey() throws Exception {
        mockMvc.perform(post("/api/vigenere/encrypt")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"text\":\"HELLO\"}"))
                .andExpect(status().isBadRequest());
    }
}
