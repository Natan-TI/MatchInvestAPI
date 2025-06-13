package com.matchinvest.rest.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.matchinvest.rest.model.AppUser;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser,Long> {
  Optional<AppUser> findByUsername(String username);
  Optional<AppUser> findByEmail(String email);
}