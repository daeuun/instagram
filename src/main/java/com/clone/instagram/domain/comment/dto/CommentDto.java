package com.clone.instagram.domain.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommentDto {
    public Long postId;
    public String content;
    public CommentDto(){}
}
