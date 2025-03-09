package com.mysite.web.login.controller;

import lombok.RequiredArgsConstructor;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mysite.web.login.service.LoginService;
import com.mysite.web.common.util.JsonResult;
import com.mysite.web.login.dto.ForgotRequestDTO;
import com.mysite.web.login.dto.ForgotResponseDTO;
import com.mysite.web.login.dto.LoginRequestDTO;
import com.mysite.web.login.dto.LoginResponseDTO;
import com.mysite.web.login.dto.SignUpRequestDTO;
import com.mysite.web.login.model.UserEntity;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class LoginController {

	@Autowired
	private LoginService loginService;

	// 로그인
	@PostMapping("/login")
	public ResponseEntity<JsonResult> login(@RequestBody LoginRequestDTO loginRequest) {
//    	System.out.println("login test::" + loginRequest);
		LoginResponseDTO auth = loginService.login(loginRequest);
		System.out.println("토큰확인::::" + auth);
		return ResponseEntity.ok(JsonResult.success(auth));
	}

	// 회원가입
	@PostMapping("/signup")
	public ResponseEntity<JsonResult> signup(@RequestBody SignUpRequestDTO signUpRequestDTO) {
		System.out.println("회원가입");
		System.out.println(signUpRequestDTO);
		loginService.exeSignUp(signUpRequestDTO);

		return ResponseEntity.ok(JsonResult.success("회원가입 성공"));
	}

	// 아이디 찾기
	@PostMapping("/forgot_id")
	public ResponseEntity<JsonResult> forgotId(@RequestBody ForgotRequestDTO forgotRequestDTO) {
		// 실제 서비스 로직 호출
		int result = loginService.forgotId(forgotRequestDTO);
//		System.out.println(forgotRequestDTO);
		if (result > 0) {
			return ResponseEntity.ok(JsonResult.success("success"));

		} else {
			return ResponseEntity.ok(JsonResult.fail("fail"));
		}
	}

	// 비밀번호 찾기
	@PostMapping("/forgot_pw")
	public ResponseEntity<JsonResult> forgotPw(@RequestBody ForgotRequestDTO forgotRequestDTO) {
		// 실제 서비스 로직 호출
		int result = loginService.forgotPw(forgotRequestDTO);
		if (result > 0) {
			return ResponseEntity.ok(JsonResult.success("success"));

		} else {
			return ResponseEntity.ok(JsonResult.fail("fail"));
		}
	}

	// 이메일 중복체크
	@PostMapping("/check_id")
	public ResponseEntity<JsonResult> checkId(@RequestBody SignUpRequestDTO signUpRequestDTO) {
		// 실제 서비스 로직 호출
		int result = loginService.checkId(signUpRequestDTO);
		if (result > 0) {
	        // 중복된 ID가 있음
	        return ResponseEntity.ok(JsonResult.success(
	            Map.of(
	                "available", false,
	                "message", "이미 사용 중인 이메일입니다."
	            )
	        ));
	    } else {
	        // 중복된 ID가 없음
	        return ResponseEntity.ok(JsonResult.success(
	            Map.of(
	                "available", true,
	                "message", "사용 가능한 이메일입니다."
	            )
	        ));
	    }
	}

}