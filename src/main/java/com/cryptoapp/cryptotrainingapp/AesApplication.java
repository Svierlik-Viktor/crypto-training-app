package com.cryptoapp.cryptotrainingapp;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import javax.annotation.PostConstruct;
import java.security.Security;
import org.bouncycastle.jce.provider.BouncyCastleProvider;


@SpringBootApplication
public class AesApplication {

    public static void main(String[] args) {
        SpringApplication.run(AesApplication.class, args);
    }

    @PostConstruct
    public void setUp() {
        Security.addProvider(new BouncyCastleProvider());
    }
}
