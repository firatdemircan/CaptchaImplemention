package com.works.captchaimplemention.utils.security.base;


import java.util.UUID;

public interface JwtUtil {

    String createToken(UUID token);

    UUID verifyToken(String token);

    Long getJwtDuration();
}
