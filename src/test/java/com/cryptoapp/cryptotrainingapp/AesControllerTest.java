package com.cryptoapp.cryptotrainingapp;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = CryptoTrainingAppApplication.class)
@AutoConfigureMockMvc
public class AesControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private static final String KEY = "abcdefghijklmnop"; // 16 символів
    private static final String IV = "1234567890abcdef";  // 16 символів
    private static final String PLAINTEXT = "Hello AES!";

    @Test
    public void testEncryptDecrypt() throws Exception {
        //  1. Encrypt
        Map<String, String> encryptRequest = new HashMap<>();
        encryptRequest.put("key", KEY);
        encryptRequest.put("iv", IV);
        encryptRequest.put("plaintext", PLAINTEXT);

        String encryptJson = objectMapper.writeValueAsString(encryptRequest);

        String ciphertext = mockMvc.perform(post("/api/aes/encrypt")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(encryptJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ciphertext").exists())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Map<String, String> encryptResponse = objectMapper.readValue(ciphertext, Map.class);
        String cipherHex = encryptResponse.get("ciphertext");

        assertThat(cipherHex).isNotEmpty();

        //  2. Decrypt
        Map<String, String> decryptRequest = new HashMap<>();
        decryptRequest.put("key", KEY);
        decryptRequest.put("iv", IV);
        decryptRequest.put("ciphertext", cipherHex);

        String decryptJson = objectMapper.writeValueAsString(decryptRequest);

        String decrypted = mockMvc.perform(post("/api/aes/decrypt")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(decryptJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.plaintext").value(PLAINTEXT))
                .andReturn()
                .getResponse()
                .getContentAsString();

        Map<String, String> decryptResponse = objectMapper.readValue(decrypted, Map.class);
        assertThat(decryptResponse.get("plaintext")).isEqualTo(PLAINTEXT);
    }

}

