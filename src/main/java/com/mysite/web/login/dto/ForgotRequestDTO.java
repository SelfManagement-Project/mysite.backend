package com.mysite.web.login.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ForgotRequestDTO {
	private String email;
    private String password;
    private String username;
    private String userHp;
    private String userAddress;
    private String residentNum;
    
}
