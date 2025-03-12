package com.mysite.web.login.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.mysite.web.login.dto.ForgotRequestDTO;
import com.mysite.web.login.dto.SignUpRequestDTO;
import com.mysite.web.login.model.UserEntity;

@Mapper
public interface LoginMapper {
    UserEntity findByEmail(String email);
    int exeSignUp(SignUpRequestDTO request);
    void exeUpdateLastLogin(String email);
    UserEntity findByNamePhone(ForgotRequestDTO request);
    UserEntity findByEmailPhone(ForgotRequestDTO request);
    int FindUserPwUpdate(UserEntity userInfo);
    int exePwUpdate(ForgotRequestDTO request);
}