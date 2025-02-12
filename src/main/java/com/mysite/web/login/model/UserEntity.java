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
}