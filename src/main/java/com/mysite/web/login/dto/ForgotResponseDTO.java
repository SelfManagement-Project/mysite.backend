package com.mysite.web.login.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ForgotResponseDTO {
    private Long userId;
    private String email;
    private String username;
    private String userHp;
    private String userAddress;
    private String residentNum;
}