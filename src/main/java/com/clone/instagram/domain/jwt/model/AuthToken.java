package com.clone.instagram.domain.jwt.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class AuthToken {
    private final String tokenType;
    private final String token;
    private final String refreshToken;

    public AuthToken(String token, String refreshToken) {
        this("Bearer", token, refreshToken);
    }
}