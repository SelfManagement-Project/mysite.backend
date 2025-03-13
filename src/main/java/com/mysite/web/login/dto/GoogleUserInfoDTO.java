package com.mysite.web.login.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoogleUserInfoDTO {
    private String id;
    private String email;
    private String name;
    private String picture;
    private boolean emailVerified;
}