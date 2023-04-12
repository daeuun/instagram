package com.clone.instagram.domain.post.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class PostDto {
    public Long postId;
    public String content;
    public List<String> images;
    public Long userId;
    public PostDto(){}
}
