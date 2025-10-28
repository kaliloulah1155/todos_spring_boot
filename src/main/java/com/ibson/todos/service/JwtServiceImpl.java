package com.ibson.todos.service;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtServiceImpl implements JwtService {
	
	@Value("${spring.jwt.secret}")
	private String SECRET_KEY;
	@Value("${spring.jwt.expiration}")
	private long JWT_EXPIRATION;

	@Override
	public String extractUsername(String token) {
		// TODO Auto-generated method stub
		return extractClaim(token, Claims::getSubject);
	}
	
	private <T> T extractClaim(String token,Function<Claims, T> claimsResolver) {
		final Claims claims=extractAllClaims(token);
		return claimsResolver.apply(claims);
	}
	
	private Claims extractAllClaims(String token) {
	    return Jwts.parser()
	        .verifyWith((SecretKey) getSigningKey())      // nouvelle méthode de vérification
	        .build()
	        .parseSignedClaims(token)         // nouvelle méthode pour parser un token signé
	        .getPayload();                    // remplace getBody()
	}

	@Override
	public boolean isTokenValid(String token, UserDetails userDetails) {
		// TODO Auto-generated method stub
		final String username=extractUsername(token);
		
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}
	private boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}
	private Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

	@Override
	public String generateToken(Map<String, Object> claims, UserDetails userDetails) {
		// TODO Auto-generated method stub
		 return Jwts.builder()
			        .claims(claims)
			        .subject(userDetails.getUsername())
			        .issuedAt(new Date(System.currentTimeMillis()))
			        .expiration(new Date(System.currentTimeMillis() + JWT_EXPIRATION))
			        .signWith(getSigningKey()) // plus besoin de passer l'algo explicitement
			        .compact();
	}
	private Key getSigningKey() {
		byte[] keyBytes=Decoders.BASE64.decode(SECRET_KEY);
		return Keys.hmacShaKeyFor(keyBytes);
	}

}
