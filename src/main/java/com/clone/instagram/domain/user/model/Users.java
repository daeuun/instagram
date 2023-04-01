package com.clone.instagram.domain.user.model;

import com.clone.instagram.domain.user.dto.SignUpRequest;
import jakarta.persistence.*;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Entity
@Table(name = "USER")
@Getter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String email;
    @NotNull
    private String nickname;
    @NotNull
    private String password;
    private String profileImage;
    private boolean deleted;

    protected User() {}

    public User(SignUpRequest request) {
        this.email = request.getEmail();
        this.nickname = request.getNickname();
        this.password = request.getPassword();
    }

}
