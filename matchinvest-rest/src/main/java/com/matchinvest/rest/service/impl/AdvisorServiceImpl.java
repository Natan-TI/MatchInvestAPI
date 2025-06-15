package com.matchinvest.rest.service.impl;

import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.matchinvest.rest.dto.AdvisorRequestDTO;
import com.matchinvest.rest.dto.AdvisorResponseDTO;
import com.matchinvest.rest.mapper.AdvisorMapper;
import com.matchinvest.rest.model.Advisor;
import com.matchinvest.rest.model.AppUser;
import com.matchinvest.rest.repository.AdvisorRepository;
import com.matchinvest.rest.repository.AppUserRepository;
import com.matchinvest.rest.service.AdvisorService;

@Service
@Transactional
public class AdvisorServiceImpl implements AdvisorService {

    private final AdvisorRepository repository;
    private final AppUserRepository userRepository;
    private final AdvisorMapper mapper;

    public AdvisorServiceImpl(AdvisorRepository repository,
                              AppUserRepository userRepository,
                              AdvisorMapper mapper) {
        this.repository = repository;
        this.userRepository = userRepository;
        this.mapper = mapper;
    }
    
    private String sanitize(String html) {
        return Jsoup.clean(html, Safelist.basic());
    }

    @Override
    public AdvisorResponseDTO create(Long userId, AdvisorRequestDTO dto) {
        AppUser user = userRepository.findById(userId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));
        
        dto.setBio(Jsoup.clean(dto.getBio(), Safelist.basic()));
        Advisor entity = mapper.toEntity(dto);
        entity.setUser(user);
        entity.setFullName(user.getFullName());
        Advisor saved = repository.save(entity);
        return mapper.toDto(saved);
    }

    @Override
    public AdvisorResponseDTO update(Long id, AdvisorRequestDTO dto) {
        Advisor existing = repository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Advisor não encontrado"));
        existing.setCertifications(dto.getCertifications());
        existing.setSpecialties(dto.getSpecialties());
        existing.setBio(sanitize(dto.getBio()));
        existing.setHourlyRate(dto.getHourlyRate());
        Advisor updated = repository.save(existing);
        return mapper.toDto(updated);
    }

    @Override
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Advisor não encontrado");
        }
        repository.deleteById(id);
    }

    @Override
    public AdvisorResponseDTO getById(Long id) {
        Advisor entity = repository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Advisor não encontrado"));
        return mapper.toDto(entity);
    }

    @Override
    public Page<AdvisorResponseDTO> list(Pageable pageable) {
        return repository.findAll(pageable).map(mapper::toDto);
    }
}