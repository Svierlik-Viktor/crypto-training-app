package com.cryptoapp.cryptotrainingapp.ecdsa;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.stereotype.Service;

import java.security.*;
import java.security.spec.X509EncodedKeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

@Service
public class ECDSAService {

    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    public KeyPair generateKeyPair() throws Exception {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("EC", "BC");
        keyGen.initialize(256); // Крива P-256 (secp256r1)
        return keyGen.generateKeyPair();
    }

    public ECDSAResultExplanation signWithExplanation(String message, String base64PrivateKey) throws Exception {
        byte[] privateKeyBytes = Base64.getDecoder().decode(base64PrivateKey);
        KeyFactory keyFactory = KeyFactory.getInstance("EC", "BC");
        PrivateKey privateKey = keyFactory.generatePrivate(new PKCS8EncodedKeySpec(privateKeyBytes));

        Signature signature = Signature.getInstance("SHA256withECDSA", "BC");
        signature.initSign(privateKey);
        signature.update(message.getBytes());

        byte[] signatureBytes = signature.sign();
        String base64Sig = Base64.getEncoder().encodeToString(signatureBytes);

        ECDSAResultExplanation explanation = new ECDSAResultExplanation();
        explanation.setMessage(message);
        explanation.setBase64Signature(base64Sig);

        String html = """
            <h4>Кроки створення підпису ECDSA:</h4>
            <ul>
                <li><strong>Повідомлення:</strong> %s</li>
                <li><strong>Алгоритм підпису:</strong> SHA256withECDSA</li>
                <li><strong>Цифровий підпис (Base64):</strong> %s</li>
            </ul>
            """.formatted(message, base64Sig);
        explanation.setHtmlFormatted(html);
        return explanation;
    }

    public ECDSAResultExplanation verifyWithExplanation(String message, String base64PublicKey, String base64Signature) throws Exception {
        byte[] publicKeyBytes = Base64.getDecoder().decode(base64PublicKey);
        KeyFactory keyFactory = KeyFactory.getInstance("EC", "BC");
        PublicKey publicKey = keyFactory.generatePublic(new X509EncodedKeySpec(publicKeyBytes));

        Signature signature = Signature.getInstance("SHA256withECDSA", "BC");
        signature.initVerify(publicKey);
        signature.update(message.getBytes());

        byte[] signatureBytes = Base64.getDecoder().decode(base64Signature);
        boolean valid = signature.verify(signatureBytes);

        ECDSAResultExplanation explanation = new ECDSAResultExplanation();
        explanation.setMessage(message);
        explanation.setBase64Signature(base64Signature);
        explanation.setValid(valid);

        String html = """
            <h4>Кроки перевірки підпису ECDSA:</h4>
            <ul>
                <li><strong>Повідомлення:</strong> %s</li>
                <li><strong>Цифровий підпис (Base64):</strong> %s</li>
                <li><strong>Результат перевірки:</strong> %s</li>
            </ul>
            """.formatted(message, base64Signature, valid ? "Підпис дійсний ✅" : "Підпис недійсний ❌");
        explanation.setHtmlFormatted(html);
        return explanation;
    }

    public String encodePublicKey(PublicKey publicKey) {
        return Base64.getEncoder().encodeToString(publicKey.getEncoded());
    }

    public String encodePrivateKey(PrivateKey privateKey) {
        return Base64.getEncoder().encodeToString(privateKey.getEncoded());
    }
}
