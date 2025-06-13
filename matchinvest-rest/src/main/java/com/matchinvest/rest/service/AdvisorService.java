package com.matchinvest.rest.service;

import com.matchinvest.rest.dto.AdvisorRequestDTO;
import com.matchinvest.rest.dto.AdvisorResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AdvisorService {
	AdvisorResponseDTO create(Long userId, AdvisorRequestDTO dto);
    AdvisorResponseDTO update(Long id, AdvisorRequestDTO dto);
    void delete(Long id);
    AdvisorResponseDTO getById(Long id);
    Page<AdvisorResponseDTO> list(Pageable pageable);
}
