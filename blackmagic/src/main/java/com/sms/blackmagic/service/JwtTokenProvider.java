//package com.sms.blackmagic.service;
//
//import io.jsonwebtoken.*;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.stereotype.Component;
//
//import java.security.SignatureException;
//import java.util.Date;
//import java.util.stream.Collectors;
//
//@Component
//public class JwtTokenProvider {
//
//    @Value("${security.jwt.secret}")
//    private String jwtSecret;
//
//    @Value("${security.jwt.expiration}")
//    private long jwtExpirationInMs;
//
//    public String generateToken(Authentication authentication) {
//        String authorities = authentication.getAuthorities().stream()
//                .map(GrantedAuthority::getAuthority)
//                .collect(Collectors.joining(","));
//
//        return Jwts.builder()
//                .setSubject(authentication.getName())
//                .claim("roles", authorities)
//                .setIssuedAt(new Date())
//                .setExpiration(new Date(new Date().getTime() + jwtExpirationInMs))
//                .signWith(SignatureAlgorithm.HS512, jwtSecret)
//                .compact();
//    }
//
//    public String getUsernameFromJWT(String token) {
//        Claims claims = Jwts.parser()
//                .setSigningKey(jwtSecret)
//                .parseClaimsJws(token)
//                .getBody();
//
//        return claims.getSubject();
//    }
//
//    public boolean validateToken(String authToken) {
//        try {
//            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
//            return true;
//        } catch (SignatureException | MalformedJwtException | UnsupportedJwtException | IllegalArgumentException e) {
//            // Invalid JWT token
//        } catch (ExpiredJwtException e) {
//            // Expired JWT token
//        } catch (JwtException e) {
//            // JWT token is invalid or does not have the expected format
//        }
//        return false;
//    }
//}
