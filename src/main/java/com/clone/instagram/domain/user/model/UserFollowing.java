package com.clone.instagram.domain.user.model;

import jakarta.persistence.*;

@Entity
@Table(name = "user_following")
public class UserFollowing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user;

    @ManyToOne
    @JoinColumn(name = "following_id")
    private Users following;
}
