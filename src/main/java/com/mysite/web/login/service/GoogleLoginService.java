package com.mysite.web.login.service;

import com.mysite.web.login.dto.LoginResponseDTO;
import com.mysite.web.login.dto.GoogleUserInfoDTO;

public interface GoogleLoginService {
    
    // 구글 인증 코드로 사용자 정보 요청
    GoogleUserInfoDTO getGoogleUserInfo(String code);
    
    // 구글 로그인 처리 (로그인 또는 회원가입)
    LoginResponseDTO processGoogleLogin(String code);
}