package com.matchinvest.rest.service.impl;

import com.matchinvest.rest.dto.*;
import com.matchinvest.rest.mapper.AdvisorMapper;
import com.matchinvest.rest.model.*;
import com.matchinvest.rest.repository.*;
import com.matchinvest.rest.service.AdvisorService;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

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

    @Override
    public AdvisorResponseDTO create(Long userId, AdvisorRequestDTO dto) {
        AppUser user = userRepository.findById(userId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));
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
        existing.setBio(dto.getBio());
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