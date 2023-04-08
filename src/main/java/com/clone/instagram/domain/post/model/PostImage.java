package com.clone.instagram.domain.post.model;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "post_image")
@Getter
public class PostImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String imageUrl;
    @ManyToOne
    private Posts post;

    public PostImage(String imageUrl, Posts post) {
        this.imageUrl = imageUrl;
        this.post = post;
    }

    public PostImage() {}
}