package com.matchinvest.rest.controller;

import java.net.URI;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.matchinvest.rest.dto.InvestorRequestDTO;
import com.matchinvest.rest.dto.InvestorResponseDTO;
import com.matchinvest.rest.model.AppUser;
import com.matchinvest.rest.repository.AppUserRepository;
import com.matchinvest.rest.service.InvestorService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/investors")
public class InvestorController {
  private final InvestorService service;
  private final AppUserRepository userRepository;
  private static final Logger log = LoggerFactory.getLogger(InvestorController.class);
  public InvestorController(InvestorService service, AppUserRepository userRepository) { this.service = service; this.userRepository = userRepository;}

  @PostMapping
  public ResponseEntity<InvestorResponseDTO> create(@Valid @RequestBody InvestorRequestDTO dto, Authentication authentication) {
	  log.info("POST /api/v1/investors — payload={}", dto);
	  String username = authentication.getName();
	    AppUser user = userRepository.findByUsername(username)
	                    .orElseThrow(/* … */);
	    Long userId = user.getId();
	  
    InvestorResponseDTO created = service.create(dto, userId);
    return ResponseEntity.created(URI.create("/api/v1/investors/" + created.getId()))
                         .body(created);
  }

  @GetMapping
  public ResponseEntity<List<InvestorResponseDTO>> listAll() {
    log.info("GET /api/v1/investors");
    return ResponseEntity.ok(service.findAll());
  }

  @GetMapping("/{id}")
  public ResponseEntity<InvestorResponseDTO> getById(@PathVariable Long id) {
	log.info("GET /api/v1/investors/{}", id);
    return ResponseEntity.ok(service.findById(id));
  }

  @PutMapping("/{id}")
  public ResponseEntity<InvestorResponseDTO> update(
      @PathVariable Long id,
      @Valid @RequestBody InvestorRequestDTO dto) {
	  log.info("PUT /api/v1/investors/{} — payload={}", id, dto);
    return ResponseEntity.ok(service.update(id, dto));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
	log.info("DELETE /api/v1/investors/{}", id);
    service.delete(id);
    return ResponseEntity.noContent().build();
  }
}