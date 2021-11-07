package com.example.shop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Configuration
@EnableSwagger2 // pozwala uruchomić swaggera
@Import(BeanValidatorPluginsConfiguration.class) // pozwala wygenerować swaggera wraz z validatorami z dto
public class SwaggerConfig {

    @Bean
    Docket docket() {
        return new Docket(DocumentationType.SWAGGER_2)
                // aby każda data była przekazywana jako string
                .directModelSubstitute(LocalDateTime.class, String.class)
                .directModelSubstitute(LocalDate.class, String.class)
                .directModelSubstitute(LocalTime.class, String.class)
                // ---------------------------------------------
                // aby skanował wyłącznie controllery bo inaczej skanuje wszystkie klasy
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example.shop.controller"))
                .build();
    }
}
