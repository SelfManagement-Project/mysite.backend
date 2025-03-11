package com.mysite.web.common.service;

public interface VerificationService {

	void sendVerificationCode(String PhoneNumber);
	boolean verifyCode(String phoneNumber, String code);
	
	// 이메일 인증 관련 메서드 추가
    void sendEmailVerificationCode(String email);
    boolean verifyEmailCode(String email, String code);
}
