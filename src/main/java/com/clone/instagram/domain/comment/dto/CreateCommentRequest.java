package com.clone.instagram.domain.comment.dto;

import com.clone.instagram.domain.comment.model.Comment;
import com.clone.instagram.domain.post.model.Posts;
import com.clone.instagram.domain.user.model.Users;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CreateCommentRequest {
    public String content;
    public Long userId;
    public Long postId;
    public Long originalCommentId;

    public Comment toEntity(Users user, Posts post, Comment originalComment) {
        return new Comment(this.content, user, post, originalComment);
    }
}