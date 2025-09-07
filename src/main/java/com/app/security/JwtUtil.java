package com.app.security;

import java.security.Key; 
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {
  @Value("${app.jwt.secret}")
  private String jwtSecret;

  @Value("${app.jwt.expiration-ms}")
  private long jwtExpirationMs;

  private Key getKey(){
    return Keys.hmacShaKeyFor(jwtSecret.getBytes());
  }

  public String generateToken(String username, String role){
    Date now = new Date();
    Date exp = new Date(now.getTime() + jwtExpirationMs);
    return Jwts.builder()
        .setSubject(username)
        .claim("role", role)
        .setIssuedAt(now)
        .setExpiration(exp)
        .signWith(getKey(), SignatureAlgorithm.HS256)
        .compact();
  }

  public boolean validateToken(String token){
    try {
      Jwts.parserBuilder().setSigningKey(getKey()).build().parseClaimsJws(token);
      return true;
    } catch (JwtException | IllegalArgumentException e) {
      return false;
    }
  }

  public String getUsernameFromToken(String token){
    return Jwts.parserBuilder().setSigningKey(getKey()).build()
        .parseClaimsJws(token).getBody().getSubject();
  }

  public String getRoleFromToken(String token){
    Object r = Jwts.parserBuilder().setSigningKey(getKey()).build()
        .parseClaimsJws(token).getBody().get("role");
    return r == null ? null : r.toString();
  }
}
