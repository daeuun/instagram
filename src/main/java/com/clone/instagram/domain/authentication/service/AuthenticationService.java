package com.clone.instagram.domain.authentication.service;

import com.clone.instagram.domain.jwt.model.AuthToken;
import com.clone.instagram.domain.jwt.service.JwtTokenProvider;
import com.clone.instagram.domain.user.model.Users;
import com.clone.instagram.domain.authentication.model.RefreshToken;
import com.clone.instagram.domain.user.repository.UserRepository;
import com.clone.instagram.domain.authentication.dto.LoginRequest;
import com.clone.instagram.exception.BusinessException;
import com.clone.instagram.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    private final RefreshTokenService refreshTokenService;

    public AuthToken authenticate(LoginRequest loginRequest) {
            UsernamePasswordAuthenticationToken authenticationToken
                    = new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword());
        return jwtTokenProvider.generateTokenDto(authenticationToken);
    }

    public AuthToken refresh(String refreshToken) {
        if (jwtTokenProvider.validateToken(refreshToken)) {
            Authentication authentication = jwtTokenProvider.getAuthentication(refreshToken);

            AuthToken authToken = jwtTokenProvider.generateTokenDto(authentication);
            Users user = userRepository.findByEmailAndDeleted(authentication.getName(), false);
            RefreshToken userRefreshToken = refreshTokenService.findRefreshToken(user.getId());

            refreshTokenService.updateRefreshToken(userRefreshToken, authToken.getRefreshToken());
            return authToken;
        }
        throw new BusinessException(ErrorCode.INVALID_REFRESH_TOKEN);
    }

}