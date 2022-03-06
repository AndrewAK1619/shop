package com.example.shop.service;

import com.example.shop.model.dto.JwtTokenDto;
import com.example.shop.model.dto.LoginDto;

public interface LoginService {

    JwtTokenDto login(LoginDto loginDto);
}
