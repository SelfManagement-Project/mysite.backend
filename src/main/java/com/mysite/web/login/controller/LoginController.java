package com.mysite.web.login.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mysite.web.login.service.LoginService;
import com.mysite.web.login.util.JsonResult;
import com.mysite.web.login.dto.LoginRequestDTO;
import com.mysite.web.login.dto.LoginResponseDTO;
import com.mysite.web.login.model.UserEntity;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @PostMapping("/login")
    public ResponseEntity<JsonResult> login(@RequestBody LoginRequestDTO loginRequest) {
//    	System.out.println("login test::" + loginRequest);
    	LoginResponseDTO auth = loginService.login(loginRequest);
    	
        return ResponseEntity.ok(JsonResult.success(auth));
    }
}