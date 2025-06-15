package com.matchinvest.rest.config;

import org.springframework.context.annotation.*;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")           // aplica a todas as rotas REST
		        .allowedOriginPatterns(
		                "http://localhost:3000",    // React web em dev
		                "http://localhost:19006",   // Expo Web
		                "exp://*",                  // esquema Expo em mobile
		                "http://192.168.*:*",       // red na casa (Android/iOS)
		                "myapp://*",                 // esquema custom de app nativo
		                "http://localhost:8081"		//React native web
	            )
                .allowedMethods("GET","POST","PUT","PATCH","DELETE","OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true)          // enviar cookies ou auth headers
                .maxAge(3600);                   // cache de pré-flight em segundos
    }
}