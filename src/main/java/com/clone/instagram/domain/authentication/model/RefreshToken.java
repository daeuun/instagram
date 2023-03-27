package com.clone.instagram.domain.authentication.model;

import jakarta.persistence.Id;
import lombok.Getter;

import java.time.OffsetDateTime;

@Getter
public class RefreshToken {
    @Id
    private String id;
    private String tokenValue;
    private long userId;
    private Long timeout = 7L;
    private OffsetDateTime createdAt = OffsetDateTime.now();

    public RefreshToken(String newToken, String id) {
        this.tokenValue = newToken;
        this.id = id;
    }
}
