package com.works.captchaimplemention.utils.security;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.works.captchaimplemention.utils.security.base.JwtUtil;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
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
    public String generateToken(Authentication authentication) throws JsonProcessingException {
        com.works.captchaimplemention.model.dto.LoggedUserModel user = (com.works.captchaimplemention.model.dto.LoggedUserModel) authentication.getPrincipal();
        return generateToken(user);
    }

    @Override
    public String createToken(UUID token) {
        return null;
    }

    @Override
    public UUID verifyToken(String token) {
        return null;
    }

    @Override
    public Long getJwtDuration() {
        return null;
    }

    private String generateToken(com.works.captchaimplemention.model.dto.LoggedUserModel logged){
        return JWT.create()
                .withSubject(logged.toString())
                .withExpiresAt(new Date(System.currentTimeMillis() + JWT_TOKEN_DURATION))
                .sign(algorithm);
    }


}
