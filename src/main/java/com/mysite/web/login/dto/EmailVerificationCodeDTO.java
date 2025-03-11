package com.mysite.web.login.dto;

import lombok.Data;

@Data
public class EmailVerificationCodeDTO {
    private String email;
    private String code;
}