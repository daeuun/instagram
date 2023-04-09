package com.clone.instagram.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UpdateProfileRequest {
    private String nickname;
    private String profileImage;
    public UpdateProfileRequest() {}
}
