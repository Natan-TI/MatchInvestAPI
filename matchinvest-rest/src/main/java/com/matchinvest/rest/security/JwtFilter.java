package com.matchinvest.rest.security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtFilter extends OncePerRequestFilter {
	  private final JwtUtil jwtUtil;
	  private final UserDetailsService uds;

	  public JwtFilter(JwtUtil jwtUtil, UserDetailsService uds){
	    this.jwtUtil=jwtUtil; this.uds=uds;
	  }

	  @Override
	  protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
	      throws ServletException, IOException {
	    String header = req.getHeader("Authorization");
	    if(header!=null && header.startsWith("Bearer ")){
	      String token = header.substring(7);
	      try {
	        Claims claims = jwtUtil.parseToken(token);
	        String username = claims.getSubject();
	        if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null){
	          UserDetails ud = uds.loadUserByUsername(username);
	          UsernamePasswordAuthenticationToken auth =
	            new UsernamePasswordAuthenticationToken(ud, null, ud.getAuthorities());
	          SecurityContextHolder.getContext().setAuthentication(auth);
	        }
	      } catch (JwtException e){
	    	// token inválido
	      }
	    }
	    chain.doFilter(req,res);
	  }
	}
