package com.mysite.web.common.service.impl;

import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.mysite.web.common.service.SmsService;

import jakarta.annotation.PostConstruct;

import java.util.Random;

@Service
public class SmsServiceImpl implements SmsService {

    @Value("${coolsms.apiKey}")
    private String apiKey;

    @Value("${coolsms.apiSecret}")
    private String apiSecret;

    @Value("${coolsms.senderNumber}")
    private String senderNumber;
    
    private DefaultMessageService messageService;
    
    @PostConstruct
    public void init() {
        this.messageService = NurigoApp.INSTANCE.initialize(apiKey, apiSecret, "https://api.coolsms.co.kr");
    }
    
    // 6자리 인증번호 생성
    public String generateVerificationCode() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }
    
    // SMS 발송
    public SingleMessageSentResponse sendSms(String to, String content) {
        Message message = new Message();
        message.setFrom(senderNumber);
        message.setTo(to);
        message.setText(content);
        
        return this.messageService.sendOne(new SingleMessageSendingRequest(message));
    }
    
    // 인증번호 SMS 발송
    public String sendVerificationSms(String to) {
        String verificationCode = generateVerificationCode();
        String content = "[인증번호] " + verificationCode + " 입니다. 3분 이내에 입력해주세요.";
        
        try {
            this.sendSms(to, content);
            return verificationCode;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("SMS 발송에 실패했습니다.");
        }
    }
}