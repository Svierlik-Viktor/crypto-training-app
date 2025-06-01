package com.cryptoapp.cryptotrainingapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import jakarta.annotation.PostConstruct;
import java.security.Security;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

@SpringBootApplication
public class CryptoTrainingAppApplication {
    public static void main(String[] args) {
        SpringApplication.run(CryptoTrainingAppApplication.class, args);
    }

    @PostConstruct
    public void setUp() {
        Security.addProvider(new BouncyCastleProvider());
    }

}