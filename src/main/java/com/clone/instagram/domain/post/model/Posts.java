package com.clone.instagram.domain.post.model;

import com.clone.instagram.domain.post.dto.CreatePostRequest;
import com.clone.instagram.domain.user.model.Users;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "posts")
@Getter
public class Posts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;
    @JsonIgnore
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<PostImage> images = new ArrayList<>();
    @ManyToOne
    private Users writer;

    public Posts() {}

    public Posts(String content, Users writer) {
        this.content = content;
        this.writer = writer;
    }

    public Posts(String content, List<String> images, Users writer) {
        this.content = content;
        this.writer = writer;
    }

    public void addImage(String imageUrl) {
        PostImage postImage = new PostImage(imageUrl, this);
        this.images.add(postImage);
    }

    public Posts createPost(CreatePostRequest request, Users writer) {
        return new Posts(request.content, request.images, writer);
    }

    public Posts updateContent(String content) {
        this.content = content;
        return this;
    }

    public void deleteImage(PostImage image) {
        images.remove(image);
        image.setPost(null);
    }

    public void addImage(PostImage image) {
        images.add(image);
        image.setPost(this);
    }

    public void updateImages(List<PostImage> updatedImages) {
        // 기존 이미지 삭제
        List<PostImage> currentImages = new ArrayList<>(this.images);
        currentImages.forEach(this::deleteImage);
        // 새 이미지 추가
        updatedImages.forEach(this::addImage);
    }
}
