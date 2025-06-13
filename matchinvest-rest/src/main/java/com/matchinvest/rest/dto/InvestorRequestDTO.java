package com.matchinvest.rest.dto;

import com.matchinvest.rest.model.RiskAppetite;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvestorRequestDTO {

  @NotNull @DecimalMin("0.0")
  private Double capitalAvailable;

  @NotNull
  private RiskAppetite riskAppetite;
}
