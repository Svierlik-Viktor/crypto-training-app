package com.cryptoapp.cryptotrainingapp.rsa;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.Base64;
import java.util.Arrays;

@Service
public class RSAService {

    private PrivateKey privateKey;
    private PublicKey publicKey;

    @PostConstruct
    public void setup() {
        Security.addProvider(new BouncyCastleProvider());
    }

    public void generateKeys() throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA", "BC");
        keyPairGenerator.initialize(2048);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        this.publicKey = keyPair.getPublic();
        this.privateKey = keyPair.getPrivate();
    }

    public String getPublicKeyAsString() {
        return Base64.getEncoder().encodeToString(publicKey.getEncoded());
    }

    public String getPrivateKeyAsString() {
        return Base64.getEncoder().encodeToString(privateKey.getEncoded());
    }

    public RSAResultExplanation encryptWithExplanation(String text) throws Exception {
        if (publicKey == null) throw new IllegalStateException("Ключі не згенеровані");

        RSAResultExplanation explanation = new RSAResultExplanation();
        explanation.setInputText(text);

        byte[] textBytes = text.getBytes(StandardCharsets.UTF_8);
        explanation.setInputBytes(Arrays.toString(textBytes));

        String base64Before = Base64.getEncoder().encodeToString(textBytes);
        explanation.setBase64BeforeEncryption(base64Before);

        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding", "BC");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] encryptedBytes = cipher.doFinal(textBytes);

        String encryptedBase64 = Base64.getEncoder().encodeToString(encryptedBytes);
        explanation.setEncryptedBase64(encryptedBase64);

        String html = """
            <h4>Кроки шифрування RSA:</h4>
            <ul>
                <li><strong>Вхідний текст:</strong> %s</li>
                <li><strong>Байти (UTF-8):</strong> %s</li>
                <li><strong>Base64 перед шифруванням:</strong> %s</li>
                <li><strong>RSA шифрування (M^e mod n):</strong> виконано</li>
                <li><strong>Зашифрований текст (Base64):</strong> %s</li>
            </ul>
            """.formatted(
                explanation.getInputText(),
                explanation.getInputBytes(),
                explanation.getBase64BeforeEncryption(),
                explanation.getEncryptedBase64()
        );

        explanation.setHtmlFormatted(html);
        return explanation;
    }

    public RSAResultExplanation decryptWithExplanation(String base64Text) throws Exception {
        if (privateKey == null) throw new IllegalStateException("Ключі не згенеровані");

        RSAResultExplanation explanation = new RSAResultExplanation();
        explanation.setEncryptedBase64(base64Text);

        byte[] encryptedBytes = Base64.getDecoder().decode(base64Text);
        String decodedBytes = Arrays.toString(encryptedBytes);
        explanation.setDecryptedBase64(decodedBytes);

        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding", "BC");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] decryptedBytes = cipher.doFinal(encryptedBytes);

        String decryptedText = new String(decryptedBytes, StandardCharsets.UTF_8);
        explanation.setOutputText(decryptedText);

        String html = """
            <h4>Кроки розшифрування RSA:</h4>
            <ul>
                <li><strong>Вхідний шифротекст (Base64):</strong> %s</li>
                <li><strong>Байти шифротексту:</strong> %s</li>
                <li><strong>RSA розшифрування (C^d mod n):</strong> виконано</li>
                <li><strong>Розшифрований текст:</strong> %s</li>
            </ul>
            """.formatted(
                explanation.getEncryptedBase64(),
                explanation.getDecryptedBase64(),
                explanation.getOutputText()
        );

        explanation.setHtmlFormatted(html);
        return explanation;
    }
}
