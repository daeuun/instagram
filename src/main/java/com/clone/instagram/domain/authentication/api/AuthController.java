package com.clone.instagram.domain.authentication.api;

import com.clone.instagram.domain.authentication.service.AuthenticationService;
import com.clone.instagram.domain.jwt.model.JwtDto;
import com.clone.instagram.domain.result.ResultCode;
import com.clone.instagram.domain.result.ResultResponse;
import com.clone.instagram.domain.authentication.dto.LoginRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class AuthController {

    private final AuthenticationService authenticationService;

    @GetMapping("/auth/login")
    public ResultResponse login(@RequestBody LoginRequest loginRequest) {
        JwtDto jwtDto = authenticationService.authenticate(loginRequest);
        return ResultResponse.of(ResultCode.LOGIN_SUCCESS, jwtDto);
    }
    @PostMapping("/auth/refresh")
    public ResponseEntity<JwtDto> refresh(@RequestHeader("Authorization") String refreshToken) {
        return ResponseEntity.ok(authenticationService.refresh(refreshToken));
    }

}