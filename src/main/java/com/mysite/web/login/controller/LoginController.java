package com.mysite.web.login.controller;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mysite.web.login.service.KakaoLoginService;
import com.mysite.web.login.service.LoginService;
import com.mysite.web.common.dto.PhoneNumberRequestDTO;
import com.mysite.web.common.dto.VerificationRequestDTO;
import com.mysite.web.common.service.VerificationService;
import com.mysite.web.common.util.JsonResult;
import com.mysite.web.login.dto.EmailVerificationCodeDTO;
import com.mysite.web.login.dto.EmailVerificationRequestDTO;
import com.mysite.web.login.dto.ForgotRequestDTO;
import com.mysite.web.login.dto.ForgotResponseDTO;
import com.mysite.web.login.dto.KakaoLoginRequestDTO;
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

	@Autowired
	private VerificationService verificationService;
	
	@Autowired
    private KakaoLoginService kakaoLoginService;

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
		List<UserEntity> result = loginService.forgotId(forgotRequestDTO);
		System.out.println(result);
		if (result != null) {
			return ResponseEntity.ok(JsonResult.success(result));

		} else {
			return ResponseEntity.ok(JsonResult.fail("fail"));
		}
	}

	// 비밀번호 찾기
	@PutMapping("/forgot_pw")
	public ResponseEntity<JsonResult> forgotPw(@RequestBody ForgotRequestDTO forgotRequestDTO) {
		System.out.println(forgotRequestDTO);
		// 실제 서비스 로직 호출
		int result = loginService.forgotPw(forgotRequestDTO);
		if (result > 0) {
			return ResponseEntity.ok(JsonResult.success("success"));

		} else {
			return ResponseEntity.ok(JsonResult.fail("fail"));
		}
//		return ResponseEntity.ok(JsonResult.success("test"));
	}

	// 이메일 중복체크
	@PostMapping("/check_id")
	public ResponseEntity<JsonResult> checkId(@RequestBody SignUpRequestDTO signUpRequestDTO) {
		// 실제 서비스 로직 호출
		int result = loginService.checkId(signUpRequestDTO);
		if (result > 0) {
			// 중복된 ID가 있음
			return ResponseEntity.ok(JsonResult.success(Map.of("available", false, "message", "이미 사용 중인 이메일입니다.")));
		} else {
			// 중복된 ID가 없음
			return ResponseEntity.ok(JsonResult.success(Map.of("available", true, "message", "사용 가능한 이메일입니다.")));
		}
	}

	// 인증번호 발송 API
	@PostMapping("/sms/send")
	public ResponseEntity<String> sendVerificationCode(@RequestBody PhoneNumberRequestDTO request) {
		try {

			// 국가 코드(+82) 제거
			String phoneNumber = request.getUserHp();
			if (phoneNumber.startsWith("+82")) {
				phoneNumber = "0" + phoneNumber.substring(5); // +82 제거하고 앞에 0 추가
			}
//			System.out.println("test입니다:;;;;;;;;;;;;;;" + phoneNumber);
			verificationService.sendVerificationCode(phoneNumber);

			return ResponseEntity.ok("인증번호가 발송되었습니다.");
		} catch (Exception e) {
			return ResponseEntity.badRequest().body("인증번호 발송에 실패했습니다: " + e.getMessage());
		}
	}

	// 인증번호 확인 API
	@PostMapping("/sms/verify")
	public ResponseEntity<JsonResult> verifyCode(@RequestBody VerificationRequestDTO request) {

		// 국가 코드(+82) 제거
		String phoneNumber = request.getUserHp();
		if (phoneNumber.startsWith("+82")) {
			phoneNumber = "0" + phoneNumber.substring(5); // +82 제거하고 앞에 0 추가
		}
		boolean isValid = verificationService.verifyCode(phoneNumber, request.getCode());
		
		System.out.println("isValid::::" + isValid);
		if (isValid) {
			return ResponseEntity.ok(JsonResult.success(isValid));
		} else {
			return ResponseEntity.badRequest().body(JsonResult.success(isValid));
		}
//		return ResponseEntity.ok("test");
	}
	
	// 이메일 인증번호 발송 API
    @PostMapping("/email/send")
    public ResponseEntity<JsonResult> sendEmailVerificationCode(@RequestBody EmailVerificationRequestDTO request) {
//    	System.out.println("request:::"+ request);
        try {
            verificationService.sendEmailVerificationCode(request.getEmail());
            return ResponseEntity.ok(JsonResult.success("인증번호가 발송되었습니다."));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(JsonResult.fail("인증번호 발송에 실패했습니다: " + e.getMessage()));
        }
    }

    // 이메일 인증번호 확인 API
    @PostMapping("/email/verify")
    public ResponseEntity<JsonResult> verifyEmailCode(@RequestBody EmailVerificationCodeDTO request) {
        boolean isValid = verificationService.verifyEmailCode(request.getEmail(), request.getCode());
        
        if (isValid) {
            return ResponseEntity.ok(JsonResult.success(isValid));
        } else {
            return ResponseEntity.badRequest().body(JsonResult.success(isValid));
        }
    }
	
    @PostMapping("/kakao-callback")
    public ResponseEntity<JsonResult> kakaoLogin(@RequestBody KakaoLoginRequestDTO kakaoLoginRequest) {
        LoginResponseDTO auth = kakaoLoginService.processKakaoLogin(kakaoLoginRequest.getCode());
        return ResponseEntity.ok(JsonResult.success(auth));
    }
	


}