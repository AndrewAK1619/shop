package com.example.shop.service.impl;

import com.example.shop.model.dto.JwtTokenDto;
import com.example.shop.model.dto.LoginDto;
import com.example.shop.service.LoginService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.DefaultClaims;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

    private final AuthenticationManager authenticationManager;

    @Override
    public JwtTokenDto login(LoginDto loginDto) {
        var userNamePassword = new UsernamePasswordAuthenticationToken(
                loginDto.getEmail(),
                loginDto.getPassword()
        );
        Authentication authentication = authenticationManager.authenticate(userNamePassword);
        var claims = new DefaultClaims()
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
                .setSubject(authentication.getName());

        claims.put(
                "authorities",
                authentication.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.joining(","))
        );
        return new JwtTokenDto(
                Jwts.builder()
                        .setClaims(claims)
                        .signWith(SignatureAlgorithm.HS512, "sekret")
                        .compact()
        );
    }
}
