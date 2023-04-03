package com.clone.instagram.domain.user.model;

import jakarta.persistence.*;
import org.springframework.data.annotation.Id;

@Entity
@Table(name = "user_follower")
public class UserFollower {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user;

    @ManyToOne
    @JoinColumn(name = "follower_id")
    private Users follower;

    public static UserFollower follow(Users user, Users follower) {
        UserFollower userFollower = new UserFollower();
        userFollower.user = user;
        userFollower.follower = follower;
        return userFollower;
    }
}
