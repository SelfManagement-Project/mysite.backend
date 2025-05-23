package com.mysite.web.login.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.mysite.web.login.dto.ForgotRequestDTO;
import com.mysite.web.login.dto.SignUpRequestDTO;
import com.mysite.web.login.model.UserEntity;

@Mapper
public interface LoginMapper {
    UserEntity findByEmail(String email);
    int exeSignUp(SignUpRequestDTO request);
    void exeUpdateLastLogin(String email);
    List<UserEntity> findByNamePhone(ForgotRequestDTO request);
    UserEntity findByEmailPhone(ForgotRequestDTO request);
    int FindUserPwUpdate(UserEntity userInfo);
    int exePwUpdate(ForgotRequestDTO request);
    
    // 소셜 로그인을 위한 메서드 추가
    UserEntity findBySocialId(@Param("socialId") String socialId, @Param("socialType") String socialType);
    int exeSignUpSocial(UserEntity user);
}