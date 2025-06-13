package com.matchinvest.rest.dto;

import java.math.BigDecimal;
import java.util.List;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdvisorRequestDTO {

    @NotNull
    private List<@NotBlank String> certifications;

    @NotNull
    private List<@NotBlank String> specialties;

    @Size(max = 500)
    private String bio;

    @NotNull
    @DecimalMin("0.0")
    private BigDecimal hourlyRate;
}
