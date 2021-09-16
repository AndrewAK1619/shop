package com.example.shop.security;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static com.example.shop.security.SecurityUtils.getCurrentUserEmail;

@Component
public class AuditorAwareImpl implements AuditorAware<String> {

    // wystarczy zarejestrować tą metodę jako bean w Springu i sobie radzi
    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.ofNullable(getCurrentUserEmail());
    }
}
