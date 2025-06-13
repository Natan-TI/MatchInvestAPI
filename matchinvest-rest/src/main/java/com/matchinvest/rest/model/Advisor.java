package com.matchinvest.rest.model;

import java.math.BigDecimal;
import java.util.List;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
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
@Entity
@Table(name = "advisors")
public class Advisor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String fullName;

    @ElementCollection
    @CollectionTable(name = "advisor_certifications", joinColumns = @JoinColumn(name = "advisor_id"))
    @Column(name = "certification")
    private List<String> certifications;

    @ElementCollection
    @CollectionTable(name = "advisor_specialties", joinColumns = @JoinColumn(name = "advisor_id"))
    @Column(name = "specialty")
    private List<String> specialties;

    @Size(max = 500)
    private String bio;

    @NotNull
    @DecimalMin("0.0")
    private BigDecimal hourlyRate;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private AppUser user;
}