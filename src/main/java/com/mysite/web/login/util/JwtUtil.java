package com.mysite.web.login.util;

import java.util.Date;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JwtUtil {
    private static final String SECRET_KEY = "1234"; // 비밀키, 실제 환경에서는 보다 복잡하고 안전한 키 사용 권장
    private static final long EXPIRE_TIME = 1000 * 60 * 60; // 토큰 유효 시간(여기서는 1시간으로 설정)

    public static void createTokenAndSetHeader(HttpServletResponse response, String tokenSubject) {
        String token = createToken(tokenSubject);
        log.info("Created token: {}", token);
        addResponseHeaderToken(response, token);
    }

    public static String createToken(String tokenSubject) {
        return JWT.create()
                  .withSubject(tokenSubject)
                  .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRE_TIME))
                  .sign(Algorithm.HMAC512(SECRET_KEY));
    }

    private static void addResponseHeaderToken(HttpServletResponse response, String token) {
        response.addHeader("Authorization", "Bearer " + token);
    }

    public static int getNoFromHeader(HttpServletRequest request) {
        String token = getTokenByHeader(request);

        if(token != null && checkToken(token)) {
            return Integer.parseInt(getSubjectFromToken(token));
        }

        return -1;
    }

    public static String getTokenByHeader(HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");

        if (authorization != null && authorization.startsWith("Bearer ")) {
            return authorization.substring(7);
        } else {
            log.warn("요청 문서에 토큰 없음");
            return null;
        }
    }

    public static boolean checkToken(String token) {
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC512(SECRET_KEY)).build();
            verifier.verify(token);
            log.info("토큰이 유효합니다.");
            return true;
        } catch (JWTVerificationException exception) {
            log.error("토큰이 유효하지 않습니다.", exception);
            return false;
        }
    }

    public static String getSubjectFromToken(String token) {
        try {
            DecodedJWT decodedJWT = JWT.decode(token);
            return decodedJWT.getSubject();
        } catch (JWTVerificationException exception) {
            log.error("토큰 디코딩 실패", exception);
            return null;
        }
    }
}