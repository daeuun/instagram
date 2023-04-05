package com.clone.instagram.domain.user.model;

import com.clone.instagram.domain.user.dto.UpdateProfileRequest;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@EqualsAndHashCode
public class Users {
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
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UserFollower> followers;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UserFollowing> following;

    protected Users() {}

    public Users(String email, String password, String nickname) {
        this.email = email;
        this.nickname = nickname;
        this.password = password;
        this.followers = new HashSet<>();
        this.following = new HashSet<>();
    }

    public Users(boolean deleted) {
        this.deleted = deleted;
    }

    public Users(String email, String nickname, String password, String profileImage, boolean deleted) {
    }

    public Users withdraw() {
        return new Users(this.deleted = true);
    }

    public void update(UpdateProfileRequest request) {
        this.nickname = request.getNickname();
        this.profileImage = request.getProfileImage();
    }

    public Users clone() {
        return new Users(
                this.email,
                this.nickname,
                this.password,
                this.profileImage,
                this.deleted);
    }
}
