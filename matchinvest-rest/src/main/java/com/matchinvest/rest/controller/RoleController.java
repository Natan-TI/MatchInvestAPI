package com.matchinvest.rest.controller;

import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.matchinvest.rest.model.AppUser;
import com.matchinvest.rest.repository.AppUserRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "Choose Role", description = "Endpoint para escolha de perfil na criação de conta")
public class RoleController {

	private final AppUserRepository userRepo;
	  private static final Logger log = LoggerFactory.getLogger(RoleController.class);
	  public RoleController(AppUserRepository userRepo) {
	    this.userRepo = userRepo;
	  }

	  @PostMapping("/choose-role")
	  @Operation(summary = "Decide qual perfil o usuário quer criar")
	  public ResponseEntity<Void> chooseRole(Authentication auth,
	                                         @RequestBody Map<String,String> body) {
	    log.info("POST /api/v1/auth/choose-role — payload={}", body);
	    String role = body.get("role");
	    if (!Set.of("INVESTOR","ADVISOR").contains(role)) {
	      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Role inválida");
	    }
	    AppUser user = userRepo.findByUsername(auth.getName())
	      .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
	    user.getRoles().clear();
	    user.getRoles().add("ROLE_" + role);
	    userRepo.save(user);
	    return ResponseEntity.noContent().build();
	  }
}
