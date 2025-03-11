package com.mysite.web.common.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.mysite.web.common.service.SmsService;
import com.mysite.web.common.service.VerificationService;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class VerificationServiceImpl implements VerificationService {

	@Autowired
	private SmsService smsService;

	@Autowired
	private JavaMailSender emailSender;

	// 인증코드 저장용 맵: 키는 전화번호, 값은 코드와 만료시간
	private final Map<String, VerificationData> verificationMap = new ConcurrentHashMap<>();

	public VerificationServiceImpl(SmsServiceImpl smsService, JavaMailSender emailSender) {
		this.smsService = smsService;
		this.emailSender = emailSender;
	}

	// 내부 클래스로 인증 데이터 정의
	private static class VerificationData {
		private final String code;
		private final LocalDateTime expiryTime;

		public VerificationData(String code, LocalDateTime expiryTime) {
			this.code = code;
			this.expiryTime = expiryTime;
		}
	}

	// 6자리 인증번호 생성
	private String generateVerificationCode() {
		Random random = new Random();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < 6; i++) {
			sb.append(random.nextInt(10));
		}
		return sb.toString();
	}

	// 인증번호 발송 및 저장
	public void sendVerificationCode(String phoneNumber) {
		String code = smsService.sendVerificationSms(phoneNumber);

		// 3분 후 만료되는 인증 데이터 저장
		verificationMap.put(phoneNumber, new VerificationData(code, LocalDateTime.now().plusMinutes(3)));
	}

	// 인증번호 검증
	public boolean verifyCode(String phoneNumber, String code) {
		VerificationData data = verificationMap.get(phoneNumber);

		if (data != null && data.code.equals(code) && LocalDateTime.now().isBefore(data.expiryTime)) {
			// 인증 성공 시 데이터 삭제
			verificationMap.remove(phoneNumber);
			return true;
		}

		return false;
	}

	// 이메일 인증번호 발송 (신규 추가)
	@Override
	public void sendEmailVerificationCode(String email) {
		String code = generateVerificationCode();
		System.out.println(code);

		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(email);
		message.setSubject("이메일 인증번호");
		message.setText("인증번호: " + code + "\n3분 이내에 입력해주세요.");

		System.out.println(message);
		emailSender.send(message);
		// 3분 후 만료되는 인증 데이터 저장
		verificationMap.put("EMAIL:" + email, new VerificationData(code, LocalDateTime.now().plusMinutes(3)));
	}

	// 이메일 인증번호 검증 (신규 추가)
	@Override
	public boolean verifyEmailCode(String email, String code) {
		VerificationData data = verificationMap.get("EMAIL:" + email);

		if (data != null && data.code.equals(code) && LocalDateTime.now().isBefore(data.expiryTime)) {
			// 인증 성공 시 데이터 삭제
			verificationMap.remove("EMAIL:" + email);
			return true;
		}

		return false;
	}

	// 주기적으로 만료된 인증 데이터 정리 (선택사항)
	public void cleanupExpiredCodes() {
		LocalDateTime now = LocalDateTime.now();
		verificationMap.entrySet().removeIf(entry -> entry.getValue().expiryTime.isBefore(now));
	}
}