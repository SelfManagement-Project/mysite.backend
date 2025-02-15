package com.mysite.web.login.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.mysite.web.login.dto.SignUpRequestDTO;
import com.mysite.web.login.model.UserEntity;

@Mapper
public interface LoginMapper {
    UserEntity findByEmail(String email);
    int exeSignUp(SignUpRequestDTO request);
    void exeUpdateLastLogin(String email);
}