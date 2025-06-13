package com.matchinvest.rest.config;

import org.springframework.context.annotation.*;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")           // aplica a todas as rotas REST
                .allowedOriginPatterns(                 // front-end e mobile
                    "http://localhost:3000",     // React/Vue/etc em dev
                    "http://localhost:19006",	 // Expo Web
                    "http://localhost:8081",	 // Expo
                    "myapp://*",                 // esquema customizado de apps mobile
                    "*"                          // '*' para abrir a todos 
                )
                .allowedMethods("GET","POST","PUT","PATCH","DELETE","OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true)          // enviar cookies ou auth headers
                .maxAge(3600);                   // cache de pré-flight em segundos
    }
}