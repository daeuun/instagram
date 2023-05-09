package com.clone.instagram.domain.user.model;

import com.clone.instagram.domain.message.model.ChatRoom;
import com.clone.instagram.domain.user.dto.UpdateProfileRequest;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.List;
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
    private Boolean deleted = false;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<UserFollower> followers;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<UserFollowing> following;

    // N+N 관계 써도 되는건가?
    @ManyToMany(mappedBy = "users")
    private List<ChatRoom> chatRooms;

    protected Users() {}

    public Users(String email, String password, String nickname) {
        this.email = email;
        this.nickname = nickname;
        this.password = password;
        this.followers = new HashSet<>();
        this.following = new HashSet<>();
    }

    public boolean isDeleted () {
        return this.deleted;
    }

    public Users(String email, String nickname, String password, String profileImage, boolean deleted) {
    }

    public Users withdraw() {
        this.deleted = true;
        return this;
    }

    public Users updateProfile(UpdateProfileRequest request) {
        this.nickname = request.getNickname();
        this.profileImage = request.getProfileImage();
        return this;
    }
}
