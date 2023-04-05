package com.clone.instagram.domain.user.service;

import com.clone.instagram.domain.authentication.model.RefreshToken;
import com.clone.instagram.domain.authentication.service.RefreshTokenService;
import com.clone.instagram.domain.user.dto.SignUpRequest;
import com.clone.instagram.domain.user.model.Users;
import com.clone.instagram.domain.user.repository.UserRepository;
import com.clone.instagram.domain.user.repository.UserRepositoryCutsom;
import com.clone.instagram.domain.user.util.PasswordUtil;
import com.clone.instagram.exception.BusinessException;
import com.clone.instagram.exception.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserRepositoryCutsom userRepositoryCutsom;
    @Autowired
    private RefreshTokenService refreshTokenService;
    @Autowired
    private PasswordUtil passwordUtil;

    public void signup(SignUpRequest request) {
        Optional.ofNullable(
                userRepository.findByEmailAndDeleted(request.getEmail(), false)
        ).ifPresent(user -> {
            throw new BusinessException(ErrorCode.USER_ALREADY_EXISTS);
        });
        request.setPassword(passwordUtil.encodePassword(request.getPassword()));
        Users newUser = new Users(request.email, request.password, request.nickname);
        userRepository.save(newUser);
    }

    @Transactional
    public void withdraw() {
        Optional.ofNullable(
            userRepository.findByEmailAndDeleted(contextId(), false)
        ).ifPresentOrElse(userRepository::delete, () -> { // 유저 존재: userRepository::delete 로 유저 탈퇴처리, 유저 존재X: BusinessException
            throw new BusinessException(ErrorCode.USER_ALREADY_EXISTS);
        });
    }

    @Transactional
    public void softWithdraw() {
        Users user = userRepository.findByEmailAndDeleted(contextId(), false);
        Optional.ofNullable(user)
                .ifPresentOrElse(this::withdrawUser, () -> {
                    throw new BusinessException(ErrorCode.USER_DOES_NOT_EXISTS);
                });
    }

    private void withdrawUser(Users user) {
        user.withdraw();
        RefreshToken refreshToken = refreshTokenService.findRefreshToken(user.getId());
        Optional.ofNullable(refreshToken).ifPresent(refreshTokenService::deleteRefreshToken);
        userRepository.save(user);
    }

    private String contextId() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        }
        return null;
    }


}
