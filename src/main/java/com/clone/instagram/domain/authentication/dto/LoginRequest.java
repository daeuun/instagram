package com.clone.instagram.domain.authentication.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginRequest {
    public String email;
    public String password;
    public LoginRequest() {}
}
