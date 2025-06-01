package com.cryptoapp.cryptotrainingapp;

import com.cryptoapp.cryptotrainingapp.ecdsa.ECDSAResultExplanation;
import com.cryptoapp.cryptotrainingapp.ecdsa.ECDSAService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = CryptoTrainingAppApplication.class)
@AutoConfigureMockMvc
public class ECDSAControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ECDSAService ecdsaService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testGenerateKeys() throws Exception {
        KeyPair keyPair = new ECDSAService().generateKeyPair();
        PrivateKey privateKey = keyPair.getPrivate();
        PublicKey publicKey = keyPair.getPublic();

        when(ecdsaService.generateKeyPair()).thenReturn(keyPair);
        when(ecdsaService.encodePrivateKey(privateKey)).thenReturn(Base64.getEncoder().encodeToString(privateKey.getEncoded()));
        when(ecdsaService.encodePublicKey(publicKey)).thenReturn(Base64.getEncoder().encodeToString(publicKey.getEncoded()));

        mockMvc.perform(get("/api/ecdsa/generate-keys"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.privateKey").exists())
                .andExpect(jsonPath("$.publicKey").exists());
    }

    @Test
    public void testSignMessage() throws Exception {
        Map<String, String> request = new HashMap<>();
        request.put("message", "Test message");
        request.put("privateKey", "fakePrivateKey");

        ECDSAResultExplanation explanation = new ECDSAResultExplanation();
        explanation.setMessage("Test message");
        explanation.setBase64Signature("abc123==");
        explanation.setHtmlFormatted("<html>Sign Explanation</html>");

        when(ecdsaService.signWithExplanation("Test message", "fakePrivateKey")).thenReturn(explanation);

        mockMvc.perform(post("/api/ecdsa/sign")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Test message"))
                .andExpect(jsonPath("$.base64Signature").value("abc123=="))
                .andExpect(jsonPath("$.htmlFormatted").exists());
    }

    @Test
    public void testVerifySignature() throws Exception {
        Map<String, String> request = new HashMap<>();
        request.put("message", "Test message");
        request.put("publicKey", "fakePublicKey");
        request.put("signature", "abc123==");

        ECDSAResultExplanation explanation = new ECDSAResultExplanation();
        explanation.setMessage("Test message");
        explanation.setBase64Signature("abc123==");
        explanation.setValid(true);
        explanation.setHtmlFormatted("<html>Verify Explanation</html>");

        when(ecdsaService.verifyWithExplanation("Test message", "fakePublicKey", "abc123==")).thenReturn(explanation);

        mockMvc.perform(post("/api/ecdsa/verify")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Test message"))
                .andExpect(jsonPath("$.base64Signature").value("abc123=="))
                .andExpect(jsonPath("$.valid").value(true))
                .andExpect(jsonPath("$.htmlFormatted").exists());
    }
}
