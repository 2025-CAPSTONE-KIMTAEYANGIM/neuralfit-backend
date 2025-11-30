package com.example.neuralfit.common.security;

import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

@Component
public class JwtProperties {
    @Value("${jwt.secret}")
    private String JWT_ACCESS_SECRET;
    @Value("${jwt.secret}")
    private String JWT_REFRESH_SECRET;
    @Value("${jwt.refresh-token-expiration-time}")
    private Long JWT_ACCESS_EXP;
    @Value("${jwt.access-token-expiration-time}")
    private Long JWT_REFRESH_EXP;

    public SecretKey getAccessSecretKey() {
        return Keys.hmacShaKeyFor(JWT_ACCESS_SECRET.getBytes(StandardCharsets.UTF_8));
    }

    public SecretKey getRefreshSecretKey() {
        return Keys.hmacShaKeyFor(JWT_REFRESH_SECRET.getBytes(StandardCharsets.UTF_8));
    }

    public long getAccessExp() {
        return JWT_ACCESS_EXP;
    }

    public long getRefreshExp() {
        return JWT_REFRESH_EXP;
    }
}
