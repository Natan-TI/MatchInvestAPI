package com.matchinvest.rest.mapper;

import com.matchinvest.rest.dto.*;
import com.matchinvest.rest.model.Advisor;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = {})
public interface AdvisorMapper {

	@Mapping(source = "user.username", target = "username")
    @Mapping(source = "fullName", target = "fullName")
    AdvisorResponseDTO toDto(Advisor entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "fullName", ignore = true)
    @Mapping(target = "user", ignore = true)
    Advisor toEntity(AdvisorRequestDTO dto);
}