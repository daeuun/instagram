package com.clone.instagram.domain.jwt.service;

import com.clone.instagram.domain.jwt.model.AuthToken;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

// Jwt 토큰 발급 및 실제 검증
@Slf4j
@Component
public class JwtTokenProvider {
    private final Key jwtKey;

    public JwtTokenProvider(@Value("${jwt.key}") String key) {
        this.jwtKey = Keys.hmacShaKeyFor(key.getBytes(StandardCharsets.UTF_8));
    }

    private static final String BEARER_TYPE = "Bearer";
    private static final String CLAIM_JWT_TYPE_KEY = "type";
    private static final String CLAIM_AUTHORITIES_KEY = "authorities";
    private final long ACCESS_TOKEN_EXPIRES = 1000 * 10L * 1000;
    private final int REFRESH_TOKEN_EXPIRES = 60 * 60 * 24 * 14;
    private static final long SECOND_TO_MILLISECONDS = 1000L;

    public AuthToken generateTokenDto(Authentication authentication) {
        final Date now = new Date();
        final Date validity = new Date(now.getTime() + ACCESS_TOKEN_EXPIRES);
        final Date refreshExpiration = new Date(now.getTime() + REFRESH_TOKEN_EXPIRES * SECOND_TO_MILLISECONDS);

        String accessToken = Jwts.builder()
                .setSubject(authentication.getName())
                .claim(CLAIM_JWT_TYPE_KEY, BEARER_TYPE)
                .claim(CLAIM_AUTHORITIES_KEY, authentication)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(jwtKey, SignatureAlgorithm.HS512)
                .compact();

        String refreshToken = Jwts.builder()
                .setSubject(authentication.getName())
                .setExpiration(refreshExpiration)
                .claim(CLAIM_AUTHORITIES_KEY, authentication)
                .claim(CLAIM_JWT_TYPE_KEY, BEARER_TYPE)
                .setIssuedAt(now)
                .signWith(jwtKey, SignatureAlgorithm.HS512)
                .compact();
        return new AuthToken(BEARER_TYPE, accessToken, refreshToken);
    }

    public Authentication getAuthentication(String token) {
        Claims claims = parseClaims(token);
        List<SimpleGrantedAuthority> authorities = Arrays.stream(claims.get(CLAIM_AUTHORITIES_KEY).toString().split(","))
                .map(authority -> new SimpleGrantedAuthority(authority))
                .collect(Collectors.toList());
        User principal = new User(claims.getSubject(), "", authorities);
        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }

    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public boolean validateToken(String token) {
        try {
            Claims claims = parseClaims(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("Invalid JWT Token", e);
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT Token", e);
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT Token", e);
        } catch (IllegalArgumentException e) {
            log.info("JWT claims string is empty.", e);
        }
        return false;
    }

    private Claims parseClaims(String accessToken) {
        try {
            return Jwts.parserBuilder().setSigningKey(jwtKey).build().parseClaimsJws(accessToken).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }
}
