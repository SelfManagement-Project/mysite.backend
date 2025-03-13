package com.mysite.web.config;

import com.mysite.web.login.util.JwtUtil;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        
        // 로그인, 회원가입 등 인증이 필요없는 경로는 토큰 검증을 건너뜀
        if (isPermitAllUrl(request)) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = JwtUtil.getTokenByHeader(request);
        
        if (token == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write("{\"result\": \"error\", \"message\": \"no_token\"}");
            return;
        }

        if (JwtUtil.checkToken(token)) {
            String userNo = JwtUtil.getSubjectFromToken(token);
            if (userNo != null) {
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    userNo, null, Collections.emptyList()
                );
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
            filterChain.doFilter(request, response);
        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write("{\"result\": \"error\", \"message\": \"invalid_token\"}");
        }
    }

    private boolean isPermitAllUrl(HttpServletRequest request) {
        String path = request.getRequestURI();
        return path.startsWith("/api/auth/login") || 
               path.startsWith("/api/auth/signup") ||
               path.startsWith("/api/auth/forgot_id") ||
               path.startsWith("/api/auth/check_id") ||
               path.startsWith("/api/auth/forgot_pw") ||
               path.startsWith("/api/auth/sms/send") ||
               path.startsWith("/api/auth/sms/verify") ||
               path.startsWith("/api/auth/email/send") ||
               path.startsWith("/api/auth/email/verify") ||
               path.startsWith("/api/auth/kakao-callback") ||
               path.startsWith("/api/auth/naver-callback") ||
               path.startsWith("/api/auth/google-callback"); // 구글 콜백 경로 추가
    }
}