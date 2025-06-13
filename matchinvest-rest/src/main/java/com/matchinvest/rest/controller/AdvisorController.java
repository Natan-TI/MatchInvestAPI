package com.matchinvest.rest.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.matchinvest.rest.dto.AdvisorRequestDTO;
import com.matchinvest.rest.dto.AdvisorResponseDTO;
import com.matchinvest.rest.model.AppUser;
import com.matchinvest.rest.repository.AppUserRepository;
import com.matchinvest.rest.service.AdvisorService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/advisors")
public class AdvisorController {

	private final AdvisorService service;
    private final AppUserRepository userRepository;

    public AdvisorController(AdvisorService service, AppUserRepository userRepository) {
        this.service = service;
        this.userRepository = userRepository;
    }

    @PostMapping
    public ResponseEntity<AdvisorResponseDTO> create(
            Authentication auth,
            @Valid @RequestBody AdvisorRequestDTO dto) {
        String username = auth.getName();
        AppUser user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));
        AdvisorResponseDTO created = service.create(user.getId(), dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping
    public ResponseEntity<Page<AdvisorResponseDTO>> list(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<AdvisorResponseDTO> result = service.list(PageRequest.of(page,size));
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdvisorResponseDTO> get(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AdvisorResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody AdvisorRequestDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
