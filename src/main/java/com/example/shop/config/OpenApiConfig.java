package com.example.shop.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI openApi() {
        return new OpenAPI()
                .components(
                        new Components()
                                .addSecuritySchemes(
                                        "JWT_Shop_Security",
                                        new SecurityScheme()
                                                .type(SecurityScheme.Type.HTTP)
                                                .scheme("bearer")
                                                .bearerFormat("JWT")
                                )
                                .addSecuritySchemes(
                                        "Basic_Shop_security",
                                        new SecurityScheme()
                                                .type(SecurityScheme.Type.HTTP)
                                                .scheme("basic")
                                )
                )
                .info(new Info()
                        .title("Shop Api")
                        .description("Description")
                        .version("1.0")
                        .contact(new Contact()
                                .name("Andrzej Kamiński")
                                .email("konto.testowe.dev.java@gmail.com")
                                .url("https://github.com/AndrewAK1619/shop"))
                );
    }
}
