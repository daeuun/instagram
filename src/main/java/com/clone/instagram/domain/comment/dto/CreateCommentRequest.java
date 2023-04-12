package com.clone.instagram.domain.comment.dto;

import com.clone.instagram.domain.comment.model.Comment;
import com.clone.instagram.domain.post.model.Posts;
import com.clone.instagram.domain.user.model.Users;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateCommentRequest {
    String content;
    Long userId;
    Long postId;
    Long originalCommentId;

    public CreateCommentRequest(){}

    public Comment toEntity(Users user, Posts post, Comment originalComment) {
        return new Comment(this.content, user, post, originalComment);
    }
}