package com.matchinvest.rest.mapper;

import org.mapstruct.Mapper;
import com.matchinvest.rest.dto.*;
import com.matchinvest.rest.model.Investor;

@Mapper(componentModel = "spring")
public interface InvestorMapper {
  Investor toEntity(InvestorRequestDTO dto);
  InvestorResponseDTO toDTO(Investor inv);
}