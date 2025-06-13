package com.matchinvest.rest.service;

import com.matchinvest.rest.dto.InvestorRequestDTO;
import com.matchinvest.rest.dto.InvestorResponseDTO;
import java.util.List;

public interface InvestorService {
  InvestorResponseDTO create(InvestorRequestDTO dto, Long id);
  List<InvestorResponseDTO> findAll();
  InvestorResponseDTO findById(Long id);
  InvestorResponseDTO update(Long id, InvestorRequestDTO dto);
  void delete(Long id);
}