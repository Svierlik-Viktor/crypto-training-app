package com.cryptoapp.cryptotrainingapp;

import com.cryptoapp.cryptotrainingapp.tripledes.TripleDesRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = CryptoTrainingAppApplication.class)
@AutoConfigureMockMvc
public class TripleDesControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private final String plaintext = "Test TripleDES!";
    private final String key = "123456789012345678901234"; // 24 символи

    @Test
    public void testEncryptDecrypt() throws Exception {
        // 🛡 Шифрування
        TripleDesRequest encryptRequest = new TripleDesRequest();
        encryptRequest.setText(plaintext);
        encryptRequest.setKey(key);

        String encryptResponse = mockMvc.perform(post("/api/tripledes/encrypt")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(encryptRequest)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andReturn()
                .getResponse()
                .getContentAsString();

        // Витягти зашифрований текст з <code>...</code>
        Document htmlDoc = Jsoup.parse(encryptResponse);
        Elements codeTags = htmlDoc.select("code");
        assertThat(codeTags).isNotEmpty();
        String encryptedText = codeTags.last().text();
        assertThat(encryptedText).isNotBlank();

        // Розшифрування
        TripleDesRequest decryptRequest = new TripleDesRequest();
        decryptRequest.setText(encryptedText);
        decryptRequest.setKey(key);

        String decryptResponse = mockMvc.perform(post("/api/tripledes/decrypt")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(decryptRequest)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        // Перевірити, що розшифрований текст збігається з початковим
        Document decryptedDoc = Jsoup.parse(decryptResponse);
        String decryptedText = decryptedDoc.select("code").last().text();

        assertThat(decryptedText).isEqualTo(plaintext);
    }

}


