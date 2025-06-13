package com.matchinvest.rest.security;

import com.matchinvest.rest.service.impl.AppUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final AppUserDetailsService uds;
    private final JwtUtil jwtUtil;

    public SecurityConfig(AppUserDetailsService uds, JwtUtil jwtUtil) {
        this.uds = uds;
        this.jwtUtil = jwtUtil;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        JwtFilter jwtFilter = new JwtFilter(jwtUtil, uds);

        http
          .csrf(csrf -> csrf.disable())
          .sessionManagement(session ->
              session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
          )
          .authorizeHttpRequests(auth -> auth 
           // endpoints de autenticação e docs abertos
              .requestMatchers(
                  "/api/v1/auth/**",
                  "/swagger-ui.html",
                  "/v3/api-docs/**",
                  "/swagger-ui/**",
                  "/h2-console/**"
              ).permitAll()
          	// INVESTOR: CRUD completo em /investors
              .requestMatchers(HttpMethod.POST, "/api/v1/investors/**").hasRole("INVESTOR")
              .requestMatchers(HttpMethod.PUT, "/api/v1/investors/**").hasRole("INVESTOR")
              .requestMatchers(HttpMethod.DELETE, "/api/v1/investors/**").hasRole("INVESTOR")
              
           // GET /investors/** liberado para INVESTOR e ADVISOR
              .requestMatchers(HttpMethod.GET, "/api/v1/investors/**").hasAnyRole("INVESTOR","ADVISOR")
              
           // ADVISOR: CRUD completo em /advisors
              .requestMatchers(HttpMethod.POST,   "/api/v1/advisors/**").hasRole("ADVISOR")
              .requestMatchers(HttpMethod.PUT,    "/api/v1/advisors/**").hasRole("ADVISOR")
              .requestMatchers(HttpMethod.DELETE, "/api/v1/advisors/**").hasRole("ADVISOR")
              
           // GET /advisors/** liberado para INVESTOR e ADVISOR
              .requestMatchers(HttpMethod.GET, "/api/v1/advisors/**").hasAnyRole("INVESTOR","ADVISOR")
              .anyRequest().authenticated()
          )
          .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
          .headers(headers -> headers
              .frameOptions(frame -> frame.disable())
          );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
