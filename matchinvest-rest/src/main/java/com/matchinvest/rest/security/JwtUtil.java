package com.matchinvest.rest.security;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {
  @Value("${jwt.secret}") private String secret;
  @Value("${jwt.expiration}") private long expMillis;

  public String generateToken(UserDetails user) {
    return Jwts.builder()
      .setSubject(user.getUsername())
      .claim("roles", user.getAuthorities().stream()
         .map(GrantedAuthority::getAuthority).toList())
      .setIssuedAt(new Date())
      .setExpiration(new Date(System.currentTimeMillis()+expMillis))
      .signWith(
    		  Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8)),
    		  SignatureAlgorithm.HS256
    		)
      .compact();
  }

  public Claims parseToken(String token) {
    return Jwts.parserBuilder()
      .setSigningKey(Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8)))
      .build()
      .parseClaimsJws(token)
      .getBody();
  }
}
