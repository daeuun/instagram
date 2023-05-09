package com.clone.instagram.domain.post.dto;

import com.clone.instagram.domain.post.model.Posts;
import com.clone.instagram.domain.user.model.Users;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CreatePostRequest {
    public String content;
    public List<String> images;
    public Long userId;
    public LocalDateTime createdAt = LocalDateTime.now();

    public Posts toEntity(Users writer) {
        Posts post = new Posts(content, writer, createdAt);
        this.images.forEach(post::addImage);
        return post;
    }

}