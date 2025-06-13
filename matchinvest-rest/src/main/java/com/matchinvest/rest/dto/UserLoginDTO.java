package com.matchinvest.rest.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserLoginDTO {
	@NotBlank
	private String username;
	
	@NotBlank
	private String password;
	
	private Boolean rememberMe; 
}
