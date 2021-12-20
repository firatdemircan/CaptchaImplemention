package com.works.captchaimplemention.utils.security.base;


import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.security.core.Authentication;

import java.util.UUID;

public interface JwtUtil {


    String generateToken(Authentication authentication) throws JsonProcessingException;

    String createToken(UUID token);

    UUID verifyToken(String token);

    Long getJwtDuration();
}
