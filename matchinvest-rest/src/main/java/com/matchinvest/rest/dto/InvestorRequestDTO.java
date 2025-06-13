package com.matchinvest.rest.dto;

import com.matchinvest.rest.model.RiskAppetite;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvestorRequestDTO {
  @NotBlank @Size(max = 100)
  private String name;

  @NotNull @DecimalMin("0.0")
  private Double capitalAvailable;

  @NotNull
  private RiskAppetite riskAppetite;
}
