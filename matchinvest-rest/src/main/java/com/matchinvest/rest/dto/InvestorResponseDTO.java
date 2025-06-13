package com.matchinvest.rest.dto;

import com.matchinvest.rest.model.RiskAppetite;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvestorResponseDTO {
  private Long id;
  private String name;
  private Double capitalAvailable;
  private RiskAppetite riskAppetite;

}