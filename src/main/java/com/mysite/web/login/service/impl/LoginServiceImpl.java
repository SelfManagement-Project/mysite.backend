package com.mysite.web.login.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mysite.web.login.mapper.LoginMapper;
import com.mysite.web.login.dto.LoginRequestDTO;
import com.mysite.web.login.dto.LoginResponseDTO;
import com.mysite.web.login.dto.SignUpRequestDTO;
import com.mysite.web.login.dto.SignUpResponseDTO;
import com.mysite.web.login.service.LoginService;
import com.mysite.web.login.model.UserEntity;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

	@Autowired
	private LoginMapper loginMapper;

	@Autowired
	private PasswordEncoder passwordEncoder;

	// 로그인
	@Override
	public LoginResponseDTO login(LoginRequestDTO request) {
		UserEntity user = loginMapper.findByEmail(request.getEmail());
//		System.out.println("USER:::::" + user);
		if (user == null || !passwordEncoder.matches(request.getPassword(), user.getUserPw())) {
			throw new RuntimeException("Invalid email or password");
		}
		loginMapper.exeUpdateLastLogin(request.getEmail());
		return LoginResponseDTO.builder().userId(user.getUserId()).email(user.getUserEmail())
				.username(user.getUserName()).build();
	}

	// 회원가입
	@Override
	public int exeSignUp(SignUpRequestDTO request) {
	    // 이메일 중복 체크
	    UserEntity existingUser = loginMapper.findByEmail(request.getEmail());
	    if (existingUser != null) {
	        throw new RuntimeException("이미 존재하는 이메일입니다.");
	    }

	    try {
	        // 비밀번호 암호화
	        request.setPassword(passwordEncoder.encode(request.getPassword()));
	        
	        // 회원가입 실행
	        int count = loginMapper.exeSignUp(request);
	        
	        // 성공 응답 반환
	        return count;

	    } catch (Exception e) {
	        log.error("회원가입 처리 중 오류 발생: {}", e.getMessage(), e);
	        throw new RuntimeException("회원가입 처리 중 오류가 발생했습니다.", e);
	    }
	}

}