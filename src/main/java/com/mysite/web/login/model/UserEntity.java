package com.mysite.web.login.model;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.AllArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserEntity {
    private Long userId;
    private String userEmail;
    private String userPw;
    private String userName;
    private String createdAt;
    private String updatedAt;
    private String lastLogin;
    private boolean isActive;
    private String role;
    private String userHp;
    private String userAddress;
    private String residentNum;
    
    // 소셜 로그인을 위한 필드 추가
    private String socialId;  // 소셜 계정 ID
    private String socialType;  // 소셜 유형 (KAKAO, GOOGLE, NAVER)
}