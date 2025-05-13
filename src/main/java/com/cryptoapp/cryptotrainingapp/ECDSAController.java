package com.cryptoapp.cryptotrainingapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/ecdsa")
public class ECDSAController {

    @Autowired
    private ECDSAService ecdsaService;

    @GetMapping("/generate-keys")
    public Map<String, String> generateKeys() throws Exception {
        var keyPair = ecdsaService.generateKeyPair();
        Map<String, String> result = new HashMap<>();
        result.put("privateKey", ecdsaService.encodePrivateKey(keyPair.getPrivate()));
        result.put("publicKey", ecdsaService.encodePublicKey(keyPair.getPublic()));
        return result;
    }

    @PostMapping("/sign")
    public ECDSAResultExplanation signMessage(@RequestBody Map<String, String> payload) throws Exception {
        String message = payload.get("message");
        String privateKey = payload.get("privateKey");
        return ecdsaService.signWithExplanation(message, privateKey);
    }

    @PostMapping("/verify")
    public ECDSAResultExplanation verifySignature(@RequestBody Map<String, String> payload) throws Exception {
        String message = payload.get("message");
        String publicKey = payload.get("publicKey");
        String signature = payload.get("signature");
        return ecdsaService.verifyWithExplanation(message, publicKey, signature);
    }
}
