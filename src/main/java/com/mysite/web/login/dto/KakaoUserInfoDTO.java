package com.mysite.web.login.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KakaoUserInfoDTO {
    private Long id;  // 카카오 계정 ID
    private String email;  // 카카오 계정 이메일
    private String nickname;  // 카카오 계정 닉네임
    private String profileImage;  // 프로필 이미지 URL
}