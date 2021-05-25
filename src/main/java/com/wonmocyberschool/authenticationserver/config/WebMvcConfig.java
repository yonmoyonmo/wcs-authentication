package com.wonmocyberschool.authenticationserver.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:7000","http://localhost:8000","http://localhost:3000",
                        "http://blog.wonmocyberschool.com","https://blog.wonmocyberschool.com")
                .allowedMethods("POST","GET")
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(3600);
    }
}
