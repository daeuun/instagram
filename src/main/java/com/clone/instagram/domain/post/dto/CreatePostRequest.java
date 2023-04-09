package com.clone.instagram.domain.post.dto;

import com.clone.instagram.domain.post.model.Posts;
import com.clone.instagram.domain.user.model.Users;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class CreatePostRequest {
    public String content;
    public List<String> images;
    public Long userId;

    public CreatePostRequest(){}

    public Posts toEntity(Users writer) {
        Posts post = new Posts(content, writer);
        this.images.forEach(post::addImage);
        return post;
    }

}