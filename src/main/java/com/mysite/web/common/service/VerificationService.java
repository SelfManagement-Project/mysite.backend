package com.mysite.web.common.service;

public interface VerificationService {

	void sendVerificationCode(String PhoneNumber);
	boolean verifyCode(String phoneNumber, String code);
}
