package com.works.captchaimplemention.utils.shared;

import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@Component
public class beans {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public Algorithm keyPair() throws NoSuchAlgorithmException, InvalidKeySpecException {
        KeyPairGenerator keygen = KeyPairGenerator.getInstance("RSA");
        keygen.initialize(2048, new SecureRandom());
        KeyPair keyPair = keygen.generateKeyPair();

        KeyFactory keyFactory = KeyFactory.getInstance("RSA");

        String encodedPublic = Base64.getEncoder().encodeToString(keyPair.getPublic().getEncoded());
        String encodedPrivate = Base64.getEncoder().encodeToString(keyPair.getPrivate().getEncoded());

        RSAPublicKey publicKey = (RSAPublicKey) keyFactory.generatePublic(new X509EncodedKeySpec(Base64.getDecoder().decode(encodedPublic)));
        RSAPrivateKey privateKey = (RSAPrivateKey) keyFactory.generatePrivate(new PKCS8EncodedKeySpec(Base64.getDecoder().decode(encodedPrivate)));
        return Algorithm.RSA512(publicKey, privateKey);
    }


}
