package com.example.shop.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {        // CorsRegistry do rejestracji aplikacji
        registry.addMapping("/**")                    // wskazujemy ścieżki które udostępniamy
                .allowedHeaders("*")                            // wskazujemy headersy które pozwalamy przyjmować w Springu
                .allowedOrigins("http://localhost:4200")        // wskazujemy hosta któremu pozwalamy się ze Springiem komunikować
                .allowedMethods("*");                           // wskazujemy metody które są udostępniane przez Springa
    }
}
