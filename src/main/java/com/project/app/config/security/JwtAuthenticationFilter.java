package com.project.app.config.security;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;

public class JwtAuthenticationFilter extends GenericFilterBean {

    private final JwtTokenProvider jwtTokenProvider;
    
    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            // 헤더에서 JWT 받아오기
            String token = resolveToken((HttpServletRequest) request);
            
            // 토큰 유효성 검사
            if (token != null && jwtTokenProvider.validateToken(token)) {
                // 토큰이 유효하면 유저 정보를 가져와서 SecurityContext에 저장
                Authentication authentication = jwtTokenProvider.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
            chain.doFilter(request, response);
        } catch (IOException e) {
            // IO 관련 예외 처리
            e.printStackTrace(); // 로깅 추가
            throw e;
        } catch (ServletException e) {
            // 서블릿 관련 예외 처리
            e.printStackTrace(); // 로깅 추가
            throw e;
        } catch (RuntimeException e) {
            // 런타임 예외 처리 (토큰 검증 실패 등)
            e.printStackTrace(); // 로깅 추가
            throw e;
        } finally {
            // 필요한 경우 SecurityContext 정리
            SecurityContextHolder.clearContext();
        }
    }
    
    // Request Header에서 토큰 정보 추출
    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}