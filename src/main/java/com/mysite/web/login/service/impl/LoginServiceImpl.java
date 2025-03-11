package com.mysite.web.login.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.ValidationUtils;

import com.mysite.web.login.mapper.LoginMapper;
import com.mysite.web.common.service.IndexingService;
import com.mysite.web.login.dto.ForgotRequestDTO;
import com.mysite.web.login.dto.LoginRequestDTO;
import com.mysite.web.login.dto.LoginResponseDTO;
import com.mysite.web.login.dto.SignUpRequestDTO;
import com.mysite.web.login.dto.SignUpResponseDTO;
import com.mysite.web.login.service.LoginService;
import com.mysite.web.login.util.JwtUtil;
import com.mysite.web.login.model.UserEntity;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

	@Autowired
	private LoginMapper loginMapper;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private IndexingService indexingService;
	
	@Autowired
    private JavaMailSender emailSender;

	// 로그인
	@Override
	public LoginResponseDTO login(LoginRequestDTO request) {
		UserEntity user = loginMapper.findByEmail(request.getEmail());

		if (user == null || !passwordEncoder.matches(request.getPassword(), user.getUserPw())) {
			throw new RuntimeException("Invalid email or password");
		}

		loginMapper.exeUpdateLastLogin(request.getEmail());

		// JWT 토큰 생성
		String token = JwtUtil.createToken(user.getUserEmail(), user.getUserId(), user.getUserName());

		return LoginResponseDTO.builder().userId(user.getUserId()).email(user.getUserEmail())
				.username(user.getUserName()).token(token).build();
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

			// 회원가입 성공 시 인덱싱
			if (count > 0) {
				// 새로 생성된 사용자 ID 가져오기 (만약 매퍼에서 ID를 반환하지 않는다면 추가 조회 필요)
				UserEntity newUser = loginMapper.findByEmail(request.getEmail());
				if (newUser != null && newUser.getUserId() != null) {
					// 인덱싱 요청
					indexingService.indexRecord("user", newUser.getUserId());
				}
			}

			// 성공 응답 반환
			return count;

		} catch (Exception e) {
			log.error("회원가입 처리 중 오류 발생: {}", e.getMessage(), e);
			throw new RuntimeException("회원가입 처리 중 오류가 발생했습니다.", e);
		}
	}

	// 아이디 찾기
	@Override
	public String forgotId(ForgotRequestDTO request) {
		// 유저 체크
		UserEntity existingUser = loginMapper.findByNamePhone(request);
		if (existingUser == null) {
			throw new RuntimeException("계정이 없습니다.");
		}

		String userEmail = existingUser.getUserEmail();

		try {

			return userEmail;

		} catch (Exception e) {
			log.error("회원가입 처리 중 오류 발생: {}", e.getMessage(), e);
			throw new RuntimeException("회원가입 처리 중 오류가 발생했습니다.", e);
		}
	}

	// 비밀번호 찾기
	@Override
	public int forgotPw(ForgotRequestDTO request) {
		
//		System.out.println("request::::" + request);
		// 유저 체크
		UserEntity existingUser = loginMapper.findByEmailPhone(request);
		if (existingUser == null) {
			throw new RuntimeException("계정이 없습니다.");
		}
//		System.out.println("정보:::" + existingUser);
		try {

			return 0;

		} catch (Exception e) {
			log.error("회원가입 처리 중 오류 발생: {}", e.getMessage(), e);
			throw new RuntimeException("회원가입 처리 중 오류가 발생했습니다.", e);
		}
	}

	// 아이디 중복 체크
	@Override
	public int checkId(SignUpRequestDTO request) {
		// 유저 체크
		int count;
		try {
			UserEntity existingUser = loginMapper.findByEmail(request.getEmail());
			if (existingUser != null) {
				count = 1;
			}else {
				count = 0;
			}
			// 성공 응답 반환
			return count;

		} catch (Exception e) {
			log.error("회원가입 처리 중 오류 발생: {}", e.getMessage(), e);
			throw new RuntimeException("회원가입 처리 중 오류가 발생했습니다.", e);
		}
	}
}