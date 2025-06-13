package com.matchinvest.rest.repository;

import com.matchinvest.rest.model.Advisor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdvisorRepository extends JpaRepository<Advisor, Long> {
}