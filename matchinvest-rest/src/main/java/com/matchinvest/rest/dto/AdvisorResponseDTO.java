package com.matchinvest.rest.dto;

import java.math.BigDecimal;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdvisorResponseDTO {
	private Long id;
    private String fullName;
    private List<String> certifications;
    private List<String> specialties;
    private String bio;
    private BigDecimal hourlyRate;
    private String username;
}
