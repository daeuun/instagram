package com.clone.instagram.domain.jwt.model;

import lombok.Getter;

@Getter
public class JwtDto {
    private final String tokenType;
    private final String token;
    private final String refreshToken;

    public JwtDto(String tokenType, String token, String refreshToken) {
        this.tokenType = tokenType;
        this.token = token;
        this.refreshToken = refreshToken;
    }

    public JwtDto(String token, String refreshToken) {
        this("Bearer", token, refreshToken);
    }
}