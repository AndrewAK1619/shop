package com.example.shop.controller;

import com.example.shop.model.dto.JwtTokenDto;
import com.example.shop.model.dto.LoginDto;
import com.example.shop.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/login")
public class LoginController {

    private final LoginService loginService;

    @PostMapping
    JwtTokenDto login(@RequestBody LoginDto loginDto) {
        return loginService.login(loginDto);
    }
}
