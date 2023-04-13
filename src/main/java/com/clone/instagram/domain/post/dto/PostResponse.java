package com.clone.instagram.domain.post.dto;

import com.clone.instagram.domain.comment.model.Comment;
import com.clone.instagram.domain.post.model.Posts;
import com.clone.instagram.domain.user.model.Users;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class PostResponse {
    private Long postId;
    private String content;
    private Users user;
    private List<Comment> comments;

    public static PostResponse of(Posts post) {
        PostResponse postResponse = PostResponse.builder()
                .postId(post.getId())
                .content(post.getContent())
                .user(post.getWriter())
//                .comments()
                .build();
        return postResponse;
    }
}