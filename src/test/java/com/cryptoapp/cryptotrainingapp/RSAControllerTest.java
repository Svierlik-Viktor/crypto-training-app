package com.cryptoapp.cryptotrainingapp;

import com.cryptoapp.cryptotrainingapp.rsa.RSAResultExplanation;
import com.cryptoapp.cryptotrainingapp.rsa.RSAService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import java.util.Base64;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = CryptoTrainingAppApplication.class)
@AutoConfigureMockMvc
public class RSAControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RSAService rsaService;

    private String sampleText;
    private String sampleEncrypted;
    private String sampleHtml;

    @BeforeEach
    public void setup() throws Exception {
        sampleText = "Hello, RSA!";
        sampleEncrypted = Base64.getEncoder().encodeToString("Encrypted".getBytes());
        sampleHtml = "<html>Тест RSA</html>";

        RSAResultExplanation encryptionExplanation = new RSAResultExplanation();
        encryptionExplanation.setHtmlFormatted(sampleHtml);

        RSAResultExplanation decryptionExplanation = new RSAResultExplanation();
        decryptionExplanation.setHtmlFormatted(sampleHtml);

        when(rsaService.encryptWithExplanation(sampleText)).thenReturn(encryptionExplanation);
        when(rsaService.decryptWithExplanation(sampleEncrypted)).thenReturn(decryptionExplanation);

        when(rsaService.getPublicKeyAsString()).thenReturn("mockPublicKey");
        when(rsaService.getPrivateKeyAsString()).thenReturn("mockPrivateKey");

        doNothing().when(rsaService).generateKeys();
    }

    @Test
    public void testGenerateKeys() throws Exception {
        mockMvc.perform(get("/api/rsa/generate"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.publicKey").value("mockPublicKey"))
                .andExpect(jsonPath("$.privateKey").value("mockPrivateKey"));
    }

    @Test
    public void testEncryptEndpoint() throws Exception {
        String requestJson = "{\"text\":\"Hello, RSA!\"}";

        mockMvc.perform(post("/api/rsa/encrypt")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(content().string(sampleHtml));
    }

    @Test
    public void testDecryptEndpoint() throws Exception {
        String requestJson = String.format("{\"text\":\"%s\"}", sampleEncrypted);

        mockMvc.perform(post("/api/rsa/decrypt")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(content().string(sampleHtml));
    }

    @Test
    public void testEncryptWithException() throws Exception {
        when(rsaService.encryptWithExplanation(any())).thenThrow(new RuntimeException("Помилка шифрування"));

        String requestJson = "{\"text\":\"invalid\"}";

        mockMvc.perform(post("/api/rsa/encrypt")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string(containsString("Помилка шифрування")));
    }

    @Test
    public void testDecryptWithException() throws Exception {
        when(rsaService.decryptWithExplanation(any())).thenThrow(new RuntimeException("Помилка розшифрування"));

        String requestJson = "{\"text\":\"invalid\"}";

        mockMvc.perform(post("/api/rsa/decrypt")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string(containsString("Помилка розшифрування")));
    }

}