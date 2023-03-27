package com.clone.instagram.domain.authentication.service;

import com.clone.instagram.domain.authentication.repository.RefreshTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.clone.instagram.domain.authentication.model.RefreshToken;

@Service
public class RefreshTokenService {
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    public RefreshTokenService(RefreshTokenRepository refreshTokenRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
    }

    public RefreshToken findRefreshToken(Long id) {
        return refreshTokenRepository.findByUserId(id);
    }

    public void updateRefreshToken(RefreshToken oldToken, String newToken) {
        refreshTokenRepository.delete(oldToken);
        refreshTokenRepository.save(new RefreshToken(newToken, oldToken.getId()));
    }


}
