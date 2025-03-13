package com.mysite.web.login.service;

import com.mysite.web.login.dto.LoginResponseDTO;
import com.mysite.web.login.dto.NaverUserInfoDTO;

public interface NaverLoginService {
    
	// 네이버 인증 코드로 사용자 정보 요청
    NaverUserInfoDTO getNaverUserInfo(String code, String state);
    
    // 네이버 로그인 처리 (로그인 또는 회원가입)
    LoginResponseDTO processNaverLogin(String code, String state);
}