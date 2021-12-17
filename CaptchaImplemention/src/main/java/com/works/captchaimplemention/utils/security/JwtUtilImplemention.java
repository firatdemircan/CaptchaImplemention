package com.works.captchaimplemention.utils.security;

import com.works.captchaimplemention.utils.security.base.JwtUtil;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class JwtUtilImplemention implements JwtUtil {

    private final HttpServletRequest request;
    private final Algorithm algorithm;
    private final long JWT_TOKEN_DURATION = 1;//10 * 60 * 1000;
    private final long REFRESH_TOKEN_EXPIRE = 24 * 60 * 60 * 1000;


    @Override
    public String createToken(UUID token) {
        return generateToken(token);
    }

    @Override
    public UUID verifyToken(String token) {
        return null;
    }

    @Override
    public Long getJwtDuration() {
        return null;
    }

    private String generateToken(UUID subject){
        return JWT.create()
                .withSubject(subject.toString())
                .withExpiresAt(new Date(System.currentTimeMillis() + JWT_TOKEN_DURATION))
                .sign(algorithm);
    }


}
