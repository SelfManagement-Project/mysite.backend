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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mysite.web.login.dto.KakaoUserInfoDTO;
import com.mysite.web.login.dto.LoginResponseDTO;
import com.mysite.web.login.mapper.LoginMapper;
import com.mysite.web.login.model.UserEntity;
import com.mysite.web.login.service.KakaoLoginService;
import com.mysite.web.login.util.JwtUtil;

@Slf4j
@Service
@RequiredArgsConstructor
public class KakaoLoginServiceImpl implements KakaoLoginService {

    @Autowired
    private LoginMapper loginMapper;
    
    @Value("${kakao.client.id}")
    private String kakaoClientId;
    
    @Value("${kakao.client.secret}")
    private String kakaoClientSecret;
    
    @Value("${kakao.redirect.uri}")
    private String kakaoRedirectUri;
    
    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();
    
    @Override
    public KakaoUserInfoDTO getKakaoUserInfo(String code) {
        // 1. 인증 코드로 액세스 토큰 받기
        String accessToken = getKakaoAccessToken(code);
        
        if(accessToken == null) {
            throw new RuntimeException("카카오 액세스 토큰을 가져오는데 실패했습니다.");
        }
        
        // 2. 액세스 토큰으로 사용자 정보 요청
        return getKakaoUserInfoByToken(accessToken);
    }
    
    @Override
    public LoginResponseDTO processKakaoLogin(String code) {
        // 1. 카카오 사용자 정보 가져오기
        KakaoUserInfoDTO kakaoUserInfo = getKakaoUserInfo(code);
        
        // 2. 소셜 ID로 사용자 확인
        UserEntity user = loginMapper.findBySocialId(String.valueOf(kakaoUserInfo.getId()), "KAKAO");
        
        // 3. 사용자가 없으면 회원가입, 있으면 로그인 처리
        if (user == null) {
            // 회원가입 처리
            user = registerKakaoUser(kakaoUserInfo);
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
    private String getKakaoAccessToken(String code) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", kakaoClientId);
        params.add("redirect_uri", kakaoRedirectUri);
        params.add("code", code);
        params.add("client_secret", kakaoClientSecret);
        
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
        
        try {
            ResponseEntity<String> response = restTemplate.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                request,
                String.class
            );
            
            JsonNode jsonNode = objectMapper.readTree(response.getBody());
            return jsonNode.get("access_token").asText();
        } catch (Exception e) {
            log.error("카카오 액세스 토큰 요청 실패", e);
            return null;
        }
    }
    
    // 액세스 토큰으로 사용자 정보 요청
    private KakaoUserInfoDTO getKakaoUserInfoByToken(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setBearerAuth(accessToken);
        
        HttpEntity<?> request = new HttpEntity<>(headers);
        
        try {
            ResponseEntity<String> response = restTemplate.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.GET,
                request,
                String.class
            );
            
            JsonNode jsonNode = objectMapper.readTree(response.getBody());
            Long id = jsonNode.get("id").asLong();
            
            String email = "";
            String nickname = "";
            String profileImage = "";
            
            if (jsonNode.has("kakao_account")) {
                JsonNode kakaoAccount = jsonNode.get("kakao_account");
                if (kakaoAccount.has("email")) {
                    email = kakaoAccount.get("email").asText();
                }
                
                if (kakaoAccount.has("profile")) {
                    JsonNode profile = kakaoAccount.get("profile");
                    if (profile.has("nickname")) {
                        nickname = profile.get("nickname").asText();
                    }
                    if (profile.has("profile_image_url")) {
                        profileImage = profile.get("profile_image_url").asText();
                    }
                }
            }
            
            return KakaoUserInfoDTO.builder()
                    .id(id)
                    .email(email)
                    .nickname(nickname)
                    .profileImage(profileImage)
                    .build();
            
        } catch (Exception e) {
            log.error("카카오 사용자 정보 요청 실패", e);
            throw new RuntimeException("카카오 사용자 정보 요청 실패", e);
        }
    }
    
    // 카카오 사용자 등록
    private UserEntity registerKakaoUser(KakaoUserInfoDTO kakaoUserInfo) {
        // 이메일이 없는 경우 임시 이메일 생성
        String email = kakaoUserInfo.getEmail();
        if (email == null || email.isEmpty()) {
            email = "kakao_" + kakaoUserInfo.getId() + "@kakao.com";
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
        user.setUserName(kakaoUserInfo.getNickname());
        user.setUserPw(""); // 소셜 로그인은 비밀번호 없음
        user.setActive(true);
        user.setRole("USER");
        user.setSocialId(String.valueOf(kakaoUserInfo.getId()));
        user.setSocialType("KAKAO");
        
        // 회원가입 처리
        loginMapper.exeSignUpSocial(user);
        
        // 등록된 사용자 조회
        return loginMapper.findBySocialId(String.valueOf(kakaoUserInfo.getId()), "KAKAO");
    }
}