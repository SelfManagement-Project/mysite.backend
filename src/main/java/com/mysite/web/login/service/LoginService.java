package com.mysite.web.login.service;

import com.mysite.web.login.dto.ForgotRequestDTO;
import com.mysite.web.login.dto.LoginRequestDTO;
import com.mysite.web.login.dto.LoginResponseDTO;
import com.mysite.web.login.dto.SignUpRequestDTO;
import com.mysite.web.login.dto.SignUpResponseDTO;

public interface LoginService {
    LoginResponseDTO login(LoginRequestDTO request);
    int exeSignUp(SignUpRequestDTO request);
    String forgotId(ForgotRequestDTO request);
    int forgotPw(ForgotRequestDTO request);
    int checkId(SignUpRequestDTO request);
}