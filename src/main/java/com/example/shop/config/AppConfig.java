package com.example.shop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AppConfig {

    // metoda fabrykująca (Wzorzec projektowy)

    @Bean
    // tylko do rejestracji beana w springu za pomocą metody,
    // można wstrzykiwać zależności poprzez parametry funkcji
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
