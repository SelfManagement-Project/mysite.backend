package com.mysite.web.login.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mysite.web.login.service.LoginService;
import com.mysite.web.login.util.JsonResult;
import com.mysite.web.login.dto.LoginRequestDTO;
import com.mysite.web.login.dto.LoginResponseDTO;
import com.mysite.web.login.dto.SignUpRequestDTO;
import com.mysite.web.login.model.UserEntity;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class LoginController {

	private final LoginService loginService;

	// 로그인
	@PostMapping("/login")
	public ResponseEntity<JsonResult> login(@RequestBody LoginRequestDTO loginRequest) {
//    	System.out.println("login test::" + loginRequest);
		LoginResponseDTO auth = loginService.login(loginRequest);

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

}