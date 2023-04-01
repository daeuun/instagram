package com.clone.instagram.domain.authentication.service;

import com.clone.instagram.domain.jwt.model.JwtDto;
import com.clone.instagram.domain.jwt.service.JwtTokenProvider;
import com.clone.instagram.domain.user.model.Users;
import com.clone.instagram.domain.authentication.model.RefreshToken;
import com.clone.instagram.domain.user.repository.UserRepository;
import com.clone.instagram.domain.authentication.dto.LoginRequest;
import com.clone.instagram.exception.BusinessException;
import com.clone.instagram.exception.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    @Autowired
    private AuthenticationManagerBuilder authenticationManagerBuilder;

    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RefreshTokenService refreshTokenService;

    public AuthenticationService(AuthenticationManagerBuilder authenticationManagerBuilder, JwtTokenProvider jwtTokenProvider) {
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public JwtDto authenticate(LoginRequest loginRequest) {
        UsernamePasswordAuthenticationToken authenticationToken
                = new UsernamePasswordAuthenticationToken(loginRequest.getNickname(), loginRequest.getNickname());
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        return jwtTokenProvider.generateTokenDto(authentication);
    }

    public JwtDto refresh(String refreshToken) {
        if (jwtTokenProvider.validateToken(refreshToken)) {
            Authentication authentication = jwtTokenProvider.getAuthentication(refreshToken);

            JwtDto jwtDto = jwtTokenProvider.generateTokenDto(authentication);
            Users user = userRepository.findByEmailAndDeleted(authentication.getName(), false);
            RefreshToken userRefreshToken = refreshTokenService.findRefreshToken(user.getId());

            refreshTokenService.updateRefreshToken(userRefreshToken, jwtDto.getRefreshToken());
            return jwtDto;
        }
        throw new BusinessException(ErrorCode.INVALID_REFRESH_TOKEN);
    }

}