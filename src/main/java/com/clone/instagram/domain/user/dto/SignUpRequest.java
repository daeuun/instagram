package com.clone.instagram.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
public class SignUpRequest {
    @NotBlank(message = "Email cannot be blank")
    @Email(message = "유효한 이메일 형식으로 입력해주세요")
    public String email;
    @NotBlank(message = "Password cannot be blank")
    @Size(min = 8, message = "비밀번호는 8글자 이상입니다")
    public String password;
    @NotBlank(message = "Nickname cannot be blank")
    public String nickname;

}
