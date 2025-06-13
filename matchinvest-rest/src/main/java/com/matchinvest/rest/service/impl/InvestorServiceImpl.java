package com.matchinvest.rest.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.matchinvest.rest.dto.InvestorRequestDTO;
import com.matchinvest.rest.dto.InvestorResponseDTO;
import com.matchinvest.rest.exception.ResourceNotFoundException;
import com.matchinvest.rest.mapper.InvestorMapper;
import com.matchinvest.rest.model.AppUser;
import com.matchinvest.rest.model.Investor;
import com.matchinvest.rest.repository.AppUserRepository;
import com.matchinvest.rest.repository.InvestorRepository;
import com.matchinvest.rest.service.InvestorService;

import jakarta.persistence.EntityNotFoundException;

@Service
public class InvestorServiceImpl implements InvestorService {
  private final InvestorRepository repo;
  private final AppUserRepository userRepo;
  private final InvestorMapper mapper;

  public InvestorServiceImpl(InvestorRepository repo, AppUserRepository userRepo, InvestorMapper mapper) {
    this.repo = repo;
    this.userRepo = userRepo;
    this.mapper = mapper;
  }

  @Override
  public InvestorResponseDTO create(InvestorRequestDTO dto, Long userId) {
    AppUser user = userRepo.findById(userId)
        .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));

    // monta a entidade a partir do DTO
    Investor inv = mapper.toEntity(dto);

    // associa o usuário e puxa o nome completo dele
    inv.setUser(user);
    inv.setName(user.getFullName());

    Investor saved = repo.save(inv);
    return mapper.toDTO(saved);
  }

  @Override
  public List<InvestorResponseDTO> findAll() {
    return repo.findAll().stream()
      .map(mapper::toDTO)
      .collect(Collectors.toList());
  }

  @Override
  public InvestorResponseDTO findById(Long id) {
    Investor inv = repo.findById(id)
      .orElseThrow(() -> new ResourceNotFoundException("Investor não encontrado: " + id));
    return mapper.toDTO(inv);
  }

  @Override
  public InvestorResponseDTO update(Long id, InvestorRequestDTO dto) {
    Investor existing = repo.findById(id)
      .orElseThrow(() -> new ResourceNotFoundException("Investor não encontrado: " + id));
    mapper.toEntity(dto);
    existing.setCapitalAvailable(dto.getCapitalAvailable());
    existing.setRiskAppetite(dto.getRiskAppetite());
    return mapper.toDTO(repo.save(existing));
  }

  @Override
  public void delete(Long id) {
    repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Investor não encontrado: " + id));
    repo.deleteById(id);
  }
}