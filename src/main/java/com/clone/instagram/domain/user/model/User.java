package com.clone.instagram.domain.user.model;

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
    private String nickname;
    @NotNull
    private String password;
    private String profileImage;
    private boolean deleted;
}
