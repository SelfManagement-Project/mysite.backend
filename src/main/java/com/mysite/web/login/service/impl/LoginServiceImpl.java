package com.mysite.web.login.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mysite.web.login.mapper.LoginMapper;
import com.mysite.web.login.dto.LoginRequestDTO;
import com.mysite.web.login.dto.LoginResponseDTO;
import com.mysite.web.login.service.LoginService;
import com.mysite.web.login.model.UserEntity;

@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

	private final LoginMapper loginMapper;
	private final PasswordEncoder passwordEncoder;

	@Override
	public LoginResponseDTO login(LoginRequestDTO request) {
		UserEntity user = loginMapper.findByEmail(request.getEmail());
		System.out.println();
		System.out.println("USER:::::" + user);
        if (user == null || !passwordEncoder.matches(request.getPassword(), user.getUserPw())) {
            throw new RuntimeException("Invalid email or password");
        }


		return LoginResponseDTO.builder().userId(user.getUserId()).email(user.getUserEmail())
				.username(user.getUserName()).build();
	}
}