package com.mysite.web.login.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class EmailVerificationRequestDTO {
    private String email;
}