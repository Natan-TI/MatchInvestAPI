package com.matchinvest.rest.controller;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import com.matchinvest.rest.dto.*;
import com.matchinvest.rest.service.InvestorService;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/investors")
public class InvestorController {
  private final InvestorService service;
  public InvestorController(InvestorService service) { this.service = service; }

  @PostMapping
  public ResponseEntity<InvestorResponseDTO> create(@Valid @RequestBody InvestorRequestDTO dto) {
    InvestorResponseDTO created = service.create(dto);
    return ResponseEntity.created(URI.create("/api/v1/investors/" + created.getId()))
                         .body(created);
  }

  @GetMapping
  public ResponseEntity<List<InvestorResponseDTO>> listAll() {
    return ResponseEntity.ok(service.findAll());
  }

  @GetMapping("/{id}")
  public ResponseEntity<InvestorResponseDTO> getById(@PathVariable Long id) {
    return ResponseEntity.ok(service.findById(id));
  }

  @PutMapping("/{id}")
  public ResponseEntity<InvestorResponseDTO> update(
      @PathVariable Long id,
      @Valid @RequestBody InvestorRequestDTO dto) {
    return ResponseEntity.ok(service.update(id, dto));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    service.delete(id);
    return ResponseEntity.noContent().build();
  }
}