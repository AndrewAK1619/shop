package com.example.shop.security;

import com.example.shop.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.example.shop.security.SecurityUtils.getCurrentUserEmail;

@Service
@RequiredArgsConstructor
public class SecurityService {

    private final UserService userService;

    public boolean hasAccessToUser(Long id) {
        return userService.getById(id).getEmail().equals(getCurrentUserEmail());
    }
}
