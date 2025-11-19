package com.example.neuralfit.common.security;

import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

@Component
public class JwtProperties {
    private final String JWT_ACCESS_SECRET = "86fdf8c360fc78cf2e05dd3e99de8c74ddc4df85f4565e3c22ee8dd7bbd7221c";
    private final String JWT_REFRESH_SECRET = "ab4df3abc7ef78cf2e05dd3ab2de8b74d53a2dc85fa585e3c22ee8dd7774b199f";
    private final Long JWT_ACCESS_EXP = 1000L*60*60*24;
    private final Long JWT_REFRESH_EXP = JWT_ACCESS_EXP*7;

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
