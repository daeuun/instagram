package com.clone.instagram.domain.comment.dto;

import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CommentCreateRequest {

    public String content;

    public Long userId;

    public Long postId;

    public Long originalCommentId;
}