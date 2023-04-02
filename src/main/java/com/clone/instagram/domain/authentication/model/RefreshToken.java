package com.clone.instagram.domain.authentication.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;

import java.time.OffsetDateTime;

@Getter
@Entity
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String tokenValue;
    private long userId;
    private Long timeout = 7L;
    private OffsetDateTime createdAt = OffsetDateTime.now();

    public RefreshToken(String newToken, Long id) {
        this.tokenValue = newToken;
        this.id = id;
    }

    public RefreshToken() {

    }
}
