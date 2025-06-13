package com.matchinvest.rest.service.impl;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.matchinvest.rest.model.AppUser;
import com.matchinvest.rest.repository.AppUserRepository;

@Service
public class AppUserDetailsService implements UserDetailsService {
  private final AppUserRepository repo;
  public AppUserDetailsService(AppUserRepository repo){this.repo=repo;}

  @Override
  public UserDetails loadUserByUsername(String user) throws UsernameNotFoundException {
    AppUser u = repo.findByUsername(user)
      .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));
    return User.builder()
      .username(u.getUsername())
      .password(u.getPassword())
      .authorities(u.getRoles().toArray(new String[0]))
      .build();
  }
}
