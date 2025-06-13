package com.matchinvest.rest.model;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class AppUser {
	
  @Id @GeneratedValue 
  private Long id;
  
  @NotBlank
  @Column(unique = true, nullable = false)
  private String username;
  
  @NotBlank
  @Column(nullable = false)
  private String password;
  
  @NotBlank
  @Column(nullable = false)
  private String fullName;
  
  @NotBlank
  @Email
  @Column(unique = true, nullable = false)
  private String email;
  
  @ElementCollection(fetch = FetchType.EAGER)
  @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
  @Column(name = "role")
  private Set<String> roles = new HashSet<>();
}
