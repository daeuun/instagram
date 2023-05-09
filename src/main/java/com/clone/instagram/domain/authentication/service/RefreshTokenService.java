package com.clone.instagram.domain.authentication.service;

import com.clone.instagram.domain.authentication.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.clone.instagram.domain.authentication.model.RefreshToken;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshToken findRefreshToken(Long id) {
        return refreshTokenRepository.findByUserId(id);
    }

    public void updateRefreshToken(RefreshToken oldToken, String newToken) {
        refreshTokenRepository.delete(oldToken);
        refreshTokenRepository.save(new RefreshToken(newToken, oldToken.getId()));
    }

    public void deleteRefreshToken(RefreshToken refreshToken) {
        refreshTokenRepository.delete(refreshToken);
    }
}
