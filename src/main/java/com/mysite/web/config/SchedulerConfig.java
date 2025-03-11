package com.mysite.web.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.mysite.web.common.service.impl.VerificationServiceImpl;

@Configuration
@EnableScheduling
public class SchedulerConfig {

    private final VerificationServiceImpl verificationService;
    
    public SchedulerConfig(VerificationServiceImpl verificationService) {
        this.verificationService = verificationService;
    }
    
    // 5분마다 만료된 인증 코드 정리
    @Scheduled(fixedRate = 300000)
    public void cleanupExpiredCodes() {
        verificationService.cleanupExpiredCodes();
    }
}