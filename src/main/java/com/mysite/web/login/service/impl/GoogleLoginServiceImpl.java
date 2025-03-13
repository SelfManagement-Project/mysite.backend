package com.mysite.web.login.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mysite.web.login.dto.GoogleUserInfoDTO;
import com.mysite.web.login.dto.LoginResponseDTO;
import com.mysite.web.login.mapper.LoginMapper;
import com.mysite.web.login.model.UserEntity;
import com.mysite.web.login.service.GoogleLoginService;
import com.mysite.web.login.util.JwtUtil;

@Slf4j
@Service
@RequiredArgsConstructor
public class GoogleLoginServiceImpl implements GoogleLoginService {

    @Autowired
    private LoginMapper loginMapper;
    
    @Value("${google.client.id}")
    private String googleClientId;
    
    @Value("${google.client.secret}")
    private String googleClientSecret;
    
    @Value("${google.redirect.uri}")
    private String googleRedirectUri;
    
    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();
    
    @Override
    public GoogleUserInfoDTO getGoogleUserInfo(String code) {
        // 1. 인증 코드로 액세스 토큰 받기
        String accessToken = getGoogleAccessToken(code);
        
        if(accessToken == null) {
            throw new RuntimeException("구글 액세스 토큰을 가져오는데 실패했습니다.");
        }
        
        // 2. 액세스 토큰으로 사용자 정보 요청
        return getGoogleUserInfoByToken(accessToken);
    }
    
    @Override
    public LoginResponseDTO processGoogleLogin(String code) {
        // 1. 구글 사용자 정보 가져오기
        GoogleUserInfoDTO googleUserInfo = getGoogleUserInfo(code);
        
        // 2. 소셜 ID로 사용자 확인
        UserEntity user = loginMapper.findBySocialId(googleUserInfo.getId(), "GOOGLE");
        
        // 3. 사용자가 없으면 회원가입, 있으면 로그인 처리
        if (user == null) {
            // 회원가입 처리
            user = registerGoogleUser(googleUserInfo);
        }
        
        // 4. 로그인 처리 (마지막 로그인 시간 업데이트)
        loginMapper.exeUpdateLastLogin(user.getUserEmail());
        
        // 5. JWT 토큰 생성
        String token = JwtUtil.createToken(user.getUserEmail(), user.getUserId(), user.getUserName());
        
        // 6. 응답 반환
        return LoginResponseDTO.builder()
                .userId(user.getUserId())
                .email(user.getUserEmail())
                .username(user.getUserName())
                .token(token)
                .build();
    }
    
    // 인증 코드로 액세스 토큰 요청
    private String getGoogleAccessToken(String code) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("code", code);
        params.add("client_id", googleClientId);
        params.add("client_secret", googleClientSecret);
        params.add("redirect_uri", googleRedirectUri);
        params.add("grant_type", "authorization_code");
        
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
        
        try {
            ResponseEntity<String> response = restTemplate.exchange(
                "https://oauth2.googleapis.com/token",
                HttpMethod.POST,
                request,
                String.class
            );
            
            JsonNode jsonNode = objectMapper.readTree(response.getBody());
            return jsonNode.get("access_token").asText();
        } catch (Exception e) {
            log.error("구글 액세스 토큰 요청 실패", e);
            return null;
        }
    }
    
    // 액세스 토큰으로 사용자 정보 요청
    private GoogleUserInfoDTO getGoogleUserInfoByToken(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        
        HttpEntity<?> request = new HttpEntity<>(headers);
        
        try {
            ResponseEntity<String> response = restTemplate.exchange(
                "https://www.googleapis.com/oauth2/v3/userinfo",
                HttpMethod.GET,
                request,
                String.class
            );
            
            JsonNode jsonNode = objectMapper.readTree(response.getBody());
            
            String id = jsonNode.get("sub").asText();
            String email = jsonNode.has("email") ? jsonNode.get("email").asText() : "";
            String name = jsonNode.has("name") ? jsonNode.get("name").asText() : "";
            String picture = jsonNode.has("picture") ? jsonNode.get("picture").asText() : "";
            boolean emailVerified = jsonNode.has("email_verified") && jsonNode.get("email_verified").asBoolean();
            
            return GoogleUserInfoDTO.builder()
                    .id(id)
                    .email(email)
                    .name(name)
                    .picture(picture)
                    .emailVerified(emailVerified)
                    .build();
            
        } catch (Exception e) {
            log.error("구글 사용자 정보 요청 실패", e);
            throw new RuntimeException("구글 사용자 정보 요청 실패", e);
        }
    }
    
    // 구글 사용자 등록
    private UserEntity registerGoogleUser(GoogleUserInfoDTO googleUserInfo) {
        // 이메일 확인
        String email = googleUserInfo.getEmail();
        if (email == null || email.isEmpty()) {
            email = "google_" + googleUserInfo.getId() + "@gmail.com";
        }
        
        // 중복 이메일 확인
        UserEntity existingUser = loginMapper.findByEmail(email);
        if (existingUser != null) {
            // 이미 일반 계정으로 가입한 이메일인 경우
            throw new RuntimeException("이미 가입된 이메일입니다. 일반 로그인을 이용해주세요.");
        }
        
        // 사용자 정보 준비
        UserEntity user = new UserEntity();
        user.setUserEmail(email);
        user.setUserName(googleUserInfo.getName());
        user.setUserPw(""); // 소셜 로그인은 비밀번호 없음
        user.setActive(true);
        user.setRole("USER");
        user.setSocialId(googleUserInfo.getId());
        user.setSocialType("GOOGLE");
        
        // 회원가입 처리
        loginMapper.exeSignUpSocial(user);
        
        // 등록된 사용자 조회
        return loginMapper.findBySocialId(googleUserInfo.getId(), "GOOGLE");
    }
}