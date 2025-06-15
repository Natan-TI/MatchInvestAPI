package com.matchinvest.rest.controller;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.matchinvest.rest.dto.UserLoginDTO;
import com.matchinvest.rest.dto.UserRegisterDTO;
import com.matchinvest.rest.model.AppUser;
import com.matchinvest.rest.repository.AppUserRepository;
import com.matchinvest.rest.security.JwtUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "Auth", description = "Operações de Registro e Login")
public class AuthController {

    private final AppUserRepository repo;
    private final PasswordEncoder encoder;
    private final JwtUtil jwtUtil;
    private static final Logger log = LoggerFactory.getLogger(AuthController.class);

    public AuthController(AppUserRepository repo, PasswordEncoder encoder, JwtUtil jwtUtil) {
        this.repo = repo;
        this.encoder = encoder;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    @Operation(summary = "Cria uma conta no app")
    public ResponseEntity<?> register(@Valid @RequestBody UserRegisterDTO dto) {
    	log.info("POST /api/v1/auth/register — payload={}", dto);
        if (!dto.getPassword().equals(dto.getConfirmPassword())) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "Senha e confirmação não conferem"));
        }
        if (repo.findByUsername(dto.getUsername()).isPresent()) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "Nome de usuário já registrado"));
        }
        if (repo.findByEmail(dto.getEmail()).isPresent()) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "E-mail já registrado"));
        }
        
        AppUser user = new AppUser();
        user.setUsername(dto.getUsername());
        user.setPassword(encoder.encode(dto.getPassword()));
        user.setFullName(dto.getFullName());
        user.setEmail(dto.getEmail());
        user.setRoles(Set.of("ROLE_INVESTOR"));
        repo.save(user);
        
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/login")
    @Operation(summary = "Faz login em uma conta no app para recuperar o token de autenticação")
    public ResponseEntity<Map<String, String>> login(@Valid @RequestBody UserLoginDTO dto) {
    	log.info("POST /api/v1/auth/login — payload={}", dto);
        Optional<AppUser> optUser = repo.findByUsername(dto.getUsername());
        if (optUser.isEmpty() || !encoder.matches(dto.getPassword(), optUser.get().getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Credenciais inválidas"));
        }
        AppUser user = optUser.get();
        User springUser = new User(
            user.getUsername(),
            user.getPassword(),
            user.getRoles().stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList())
        );
        String token = jwtUtil.generateToken(springUser);
        return ResponseEntity.ok(Map.of("token", token));
    }

}