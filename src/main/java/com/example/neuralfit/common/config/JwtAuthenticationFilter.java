package com.example.neuralfit.common.config;

import com.example.neuralfit.user.entity.AppUser;
import com.example.neuralfit.user.repository.AppUserRepository;
import com.example.neuralfit.common.security.AuthAuthenticationEntryPoint;
import com.example.neuralfit.common.security.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;
    private final AppUserRepository appUserRepository;
    private final AuthAuthenticationEntryPoint authAuthenticationEntryPoint;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        // JWT 파싱
        String token = resolveToken(request);

        // 헤더 포함 안된 요청의 경우 Request를 다음 필터로 넘김
        if (token == null) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            if (!jwtUtil.validateAccessToken(token)) {
                throw new BadCredentialsException("유효하지 않은 토큰입니다.");
            }
            // 토큰으로부터 사용자 ID 파싱
            Integer id = jwtUtil.getIdFromAccessToken(token);

            AppUser appUser = appUserRepository.findById(id)
                    .orElseThrow(() -> new BadCredentialsException("유효하지 않은 토큰입니다.")); //401

            /*
            if(!appUser.isValid){
                throw new new BadCredentialsException("유효하지 않은 토큰입니다.");
            }
            */

            //Security Context에 사용자 ID 저장
            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(appUser, null, appUser.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authToken);

            filterChain.doFilter(request, response);
        } catch (AuthenticationException e) {
            SecurityContextHolder.clearContext();
            authAuthenticationEntryPoint.commence(request, response, e);
        }
    }

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }

        return null;
    }
}
