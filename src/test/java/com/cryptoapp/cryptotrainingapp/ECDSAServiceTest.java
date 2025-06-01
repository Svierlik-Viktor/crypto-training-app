package com.cryptoapp.cryptotrainingapp;

import com.cryptoapp.cryptotrainingapp.ecdsa.ECDSAResultExplanation;
import com.cryptoapp.cryptotrainingapp.ecdsa.ECDSAService;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ECDSAServiceTest {
    private final ECDSAService service = new ECDSAService();

    @Test
    public void testSignAndVerifyValidSignature() throws Exception {
        String message = "Hello ECDSA!";

        // Генерація ключів
        var keyPair = service.generateKeyPair();
        String privateKeyBase64 = service.encodePrivateKey(keyPair.getPrivate());
        String publicKeyBase64 = service.encodePublicKey(keyPair.getPublic());

        // Підпис
        ECDSAResultExplanation signed = service.signWithExplanation(message, privateKeyBase64);
        assertNotNull(signed.getBase64Signature());

        // Перевірка
        ECDSAResultExplanation verified = service.verifyWithExplanation(message, publicKeyBase64, signed.getBase64Signature());
        assertTrue(verified.isValid());
    }

    @Test
    public void testVerifyInvalidSignature() throws Exception {
        String message = "Message A";
        String tamperedMessage = "Message B";

        var keyPair = service.generateKeyPair();
        String privateKeyBase64 = service.encodePrivateKey(keyPair.getPrivate());
        String publicKeyBase64 = service.encodePublicKey(keyPair.getPublic());

        ECDSAResultExplanation signed = service.signWithExplanation(message, privateKeyBase64);

        ECDSAResultExplanation verified = service.verifyWithExplanation(tamperedMessage, publicKeyBase64, signed.getBase64Signature());

        assertFalse(verified.isValid());
    }

    @Test
    public void testSignatureIsDifferentForDifferentMessages() throws Exception {
        var keyPair = service.generateKeyPair();
        String privateKeyBase64 = service.encodePrivateKey(keyPair.getPrivate());

        ECDSAResultExplanation sig1 = service.signWithExplanation("msg1", privateKeyBase64);
        ECDSAResultExplanation sig2 = service.signWithExplanation("msg2", privateKeyBase64);

        assertNotEquals(sig1.getBase64Signature(), sig2.getBase64Signature());
    }

}