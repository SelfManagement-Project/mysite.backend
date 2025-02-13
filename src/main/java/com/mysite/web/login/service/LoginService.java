package com.mysite.web.login.service;

import com.mysite.web.login.dto.LoginRequestDTO;
import com.mysite.web.login.dto.LoginResponseDTO;
import com.mysite.web.login.dto.SignUpRequestDTO;
import com.mysite.web.login.dto.SignUpResponseDTO;

public interface LoginService {
    LoginResponseDTO login(LoginRequestDTO request);
    int exeSignUp(SignUpRequestDTO request);
}