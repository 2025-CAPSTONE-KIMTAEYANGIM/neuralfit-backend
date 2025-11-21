package com.example.neuralfit.common.security;

import com.example.neuralfit.user.entity.AppUser;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.stream.Collectors;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtUtil {
    private final JwtProperties jwtProperties;

    // Access 토큰 생성
    public String generateAccessToken(Authentication auth) {
        AppUser user = (AppUser) auth.getPrincipal();

        String authorities = auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        return Jwts.builder()
                .subject(user.getId().toString())
                .claim("auth", authorities)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + jwtProperties.getAccessExp()))
                .signWith(jwtProperties.getAccessSecretKey())
                .compact();
    }

    // Access 토큰 생성
    public String generateRefreshToken(Authentication auth) {
        AppUser appUser = (AppUser) auth.getPrincipal();

        return Jwts.builder()
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + jwtProperties.getRefreshExp()))
                .subject(appUser.getId().toString())
                .signWith(jwtProperties.getRefreshSecretKey())
                .compact();
    }

    public boolean validateAccessToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(jwtProperties.getAccessSecretKey())
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            log.warn("잘못된 JWT 서명입니다: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            log.warn("JWT 토큰이 만료되었습니다: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.warn("지원하지 않는 JWT 토큰입니다: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.warn("JWT 토큰이 비어 있습니다: {}", e.getMessage());
        } catch (Exception e) {
            Date date = new Date();
            log.error("Invalid JWT token inspected : {} {}", e.getMessage(), date);
        }
        return false;
    }

    public Integer getIdFromAccessToken(String token) {
        Claims claims =  Jwts.parser()
                .verifyWith(jwtProperties.getAccessSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return Integer.parseInt(claims.getSubject());
    }

    public Integer getIdFromRefreshToken(String token) {
        Claims claims =  Jwts.parser()
                .verifyWith(jwtProperties.getRefreshSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return Integer.parseInt(claims.getSubject());
    }
}
