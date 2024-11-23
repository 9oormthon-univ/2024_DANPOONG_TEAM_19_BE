package org.anyonetoo.anyonetoo.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.anyonetoo.anyonetoo.jwt.JwtTokenProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilterBean {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String requestURI = httpRequest.getRequestURI();

        // 특정 API 경로 필터 예외 처리
        if (requestURI.startsWith("/api/core/auth")) {
            System.out.println("Skipping filter for URI: " + requestURI);
            chain.doFilter(request, response);
            return;
        }

        // 토큰 검증 로직
        String token = jwtTokenProvider.resolveToken(httpRequest);
        System.out.println("Token: " + token);
        if (token != null && jwtTokenProvider.validateToken(token)) {
            Authentication authentication = jwtTokenProvider.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } else {
            System.out.println("Invalid or no token");
        }

        chain.doFilter(request, response);

//        String token = jwtTokenProvider.resolveToken((HttpServletRequest) request);
//        System.out.println("Token: " + token);
//        if (token != null && jwtTokenProvider.validateToken(token)) {
//            Authentication authentication = jwtTokenProvider.getAuthentication(token);
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//        }else {
//            System.out.println("Invalid or no token");
//        }
//        chain.doFilter(request, response);
    }
}