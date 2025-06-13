package com.matchinvest.rest.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.matchinvest.rest.dto.InvestorRequestDTO;
import com.matchinvest.rest.dto.InvestorResponseDTO;
import com.matchinvest.rest.exception.ResourceNotFoundException;
import com.matchinvest.rest.mapper.InvestorMapper;
import com.matchinvest.rest.model.Investor;
import com.matchinvest.rest.repository.InvestorRepository;
import com.matchinvest.rest.service.InvestorService;

@Service
public class InvestorServiceImpl implements InvestorService {
  private final InvestorRepository repo;
  private final InvestorMapper mapper;

  public InvestorServiceImpl(InvestorRepository repo, InvestorMapper mapper) {
    this.repo = repo;
    this.mapper = mapper;
  }

  @Override
  public InvestorResponseDTO create(InvestorRequestDTO dto) {
    return mapper.toDTO(repo.save(mapper.toEntity(dto)));
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
    existing.setName(dto.getName());
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