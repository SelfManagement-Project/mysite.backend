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
import com.mysite.web.login.dto.NaverUserInfoDTO;
import com.mysite.web.login.dto.LoginResponseDTO;
import com.mysite.web.login.mapper.LoginMapper;
import com.mysite.web.login.model.UserEntity;
import com.mysite.web.login.service.NaverLoginService;
import com.mysite.web.login.util.JwtUtil;

@Slf4j
@Service
@RequiredArgsConstructor
public class NaverLoginServiceImpl implements NaverLoginService {

    @Autowired
    private LoginMapper loginMapper;
    
    @Value("${naver.client.id}")
    private String naverClientId;
    
    @Value("${naver.client.secret}")
    private String naverClientSecret;
    
    @Value("${naver.redirect.uri}")
    private String naverRedirectUri;
    
    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();
    
    @Override
    public NaverUserInfoDTO getNaverUserInfo(String code, String state) {
        // 1. 인증 코드로 액세스 토큰 받기
        String accessToken = getNaverAccessToken(code, state);
        
        if(accessToken == null) {
            throw new RuntimeException("네이버 액세스 토큰을 가져오는데 실패했습니다.");
        }
        
        // 2. 액세스 토큰으로 사용자 정보 요청
        return getNaverUserInfoByToken(accessToken);
    }
    
    @Override
    public LoginResponseDTO processNaverLogin(String code, String state) {
        // 1. 네이버 사용자 정보 가져오기
        NaverUserInfoDTO naverUserInfo = getNaverUserInfo(code, state);
        
        // 2. 소셜 ID로 사용자 확인
        UserEntity user = loginMapper.findBySocialId(naverUserInfo.getId(), "NAVER");
        
        // 3. 사용자가 없으면 회원가입, 있으면 로그인 처리
        if (user == null) {
            // 회원가입 처리
            user = registerNaverUser(naverUserInfo);
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
    private String getNaverAccessToken(String code, String state) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", naverClientId);
        params.add("client_secret", naverClientSecret);
        params.add("code", code);
        params.add("state", state);
        
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
        
        try {
            ResponseEntity<String> response = restTemplate.exchange(
                "https://nid.naver.com/oauth2.0/token",
                HttpMethod.POST,
                request,
                String.class
            );
            
            JsonNode jsonNode = objectMapper.readTree(response.getBody());
            return jsonNode.get("access_token").asText();
        } catch (Exception e) {
            log.error("네이버 액세스 토큰 요청 실패", e);
            return null;
        }
    }
    
    // 액세스 토큰으로 사용자 정보 요청
    private NaverUserInfoDTO getNaverUserInfoByToken(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setBearerAuth(accessToken);
        
        HttpEntity<?> request = new HttpEntity<>(headers);
        
        try {
            ResponseEntity<String> response = restTemplate.exchange(
                "https://openapi.naver.com/v1/nid/me",
                HttpMethod.GET,
                request,
                String.class
            );
            
            JsonNode jsonNode = objectMapper.readTree(response.getBody());
            
            // API 응답 상태 확인
            String resultcode = jsonNode.get("resultcode").asText();
            if (!"00".equals(resultcode)) {
                log.error("네이버 API 오류: {}", jsonNode.get("message").asText());
                throw new RuntimeException("네이버 API 응답 오류");
            }
            
            // 사용자 정보 추출
            JsonNode responseNode = jsonNode.get("response");
            
            String id = responseNode.get("id").asText();
            String email = responseNode.has("email") ? responseNode.get("email").asText() : "";
            String name = responseNode.has("name") ? responseNode.get("name").asText() : "";
            String nickname = responseNode.has("nickname") ? responseNode.get("nickname").asText() : "";
            String profileImage = responseNode.has("profile_image") ? responseNode.get("profile_image").asText() : "";
            
            return NaverUserInfoDTO.builder()
                    .id(id)
                    .email(email)
                    .name(name)
                    .nickname(nickname)
                    .profileImage(profileImage)
                    .build();
            
        } catch (Exception e) {
            log.error("네이버 사용자 정보 요청 실패", e);
            throw new RuntimeException("네이버 사용자 정보 요청 실패", e);
        }
    }
    
    // 네이버 사용자 등록
    private UserEntity registerNaverUser(NaverUserInfoDTO naverUserInfo) {
        // 이메일이 없는 경우 임시 이메일 생성
        String email = naverUserInfo.getEmail();
        if (email == null || email.isEmpty()) {
            email = "naver_" + naverUserInfo.getId() + "@naver.com";
        }
        
        // 중복 이메일 확인
        UserEntity existingUser = loginMapper.findByEmail(email);
        if (existingUser != null) {
            // 이미 일반 계정으로 가입한 이메일인 경우
            throw new RuntimeException("이미 가입된 이메일입니다. 일반 로그인을 이용해주세요.");
        }
        
        // 사용자 이름 설정 (닉네임이 없으면 이름 사용)
        String userName = naverUserInfo.getNickname();
        if (userName == null || userName.isEmpty()) {
            userName = naverUserInfo.getName();
        }
        
        // 사용자 정보 준비
        UserEntity user = new UserEntity();
        user.setUserEmail(email);
        user.setUserName(userName);
        user.setUserPw(""); // 소셜 로그인은 비밀번호 없음
        user.setActive(true);
        user.setRole("USER");
        user.setSocialId(naverUserInfo.getId());
        user.setSocialType("NAVER");
        
        // 회원가입 처리
        loginMapper.exeSignUpSocial(user);
        
        // 등록된 사용자 조회
        return loginMapper.findBySocialId(naverUserInfo.getId(), "NAVER");
    }
}