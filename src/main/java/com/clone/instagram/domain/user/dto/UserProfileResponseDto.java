package com.clone.instagram.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserProfileResponseDto {
    private String email;
    private String nickname;
    private String profileImage;
}
