package com.ibson.todos.service;

import java.util.Map;

import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {
   String extractUsername(String token);
   boolean isTokenValid(String tokenString ,UserDetails userDetails);
   String generateToken(Map<String, Object> claims,UserDetails userDetails);
}
