package com.mysite.web.login.service;

import com.mysite.web.login.dto.LoginResponseDTO;
import com.mysite.web.login.dto.KakaoUserInfoDTO;

public interface KakaoLoginService {
    
    // 카카오 인증 코드로 사용자 정보 요청
    KakaoUserInfoDTO getKakaoUserInfo(String code);
    
    // 카카오 로그인 처리 (로그인 또는 회원가입)
    LoginResponseDTO processKakaoLogin(String code);
}