package com.example.shop.config;

import com.example.shop.security.JwtAuthenticationFilter;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final ObjectMapper objectMapper;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf()
                .ignoringAntMatchers("/**")
                .and()
                .cors() // do blokowania wszystkich innych aplikacji
                .and()
                // rejestrujemy nasz JwtAuthenticationFilter
                .addFilter(new JwtAuthenticationFilter(authenticationManager(), objectMapper))
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        // stateless - czyści informacje
        // statefull - nie czyści, przechowuje informacje
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // włożyliśmy do security userDetailsService, passwordEncoder
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }
}
