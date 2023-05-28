package com.clone.instagram.domain.post.dto;

import com.clone.instagram.domain.comment.models.Comment;
import com.clone.instagram.domain.post.model.PostImage;
import com.clone.instagram.domain.post.model.Posts;
import com.clone.instagram.domain.user.model.Users;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
public class PostResponse {
    private Long postId;
    private String content;
    private List<String> imageUrls;
    private Users user;
    private List<Comment> comments;

    public static PostResponse of(Posts post) {
        List<PostImage> images = post.getImages();
        List<String> imageUrls = images.stream()
                .map(PostImage::getImageUrl)
                .collect(Collectors.toList());
        List<Comment> comments = post.getComments().stream()
                .filter(comment -> !comment.isDeleted() && (comment.getOriginalComment() == null))
                .collect(Collectors.toList());

        PostResponse postResponse = PostResponse.builder()
                .postId(post.getId())
                .content(post.getContent())
                .imageUrls(imageUrls)
                .user(post.getWriter())
                .comments(comments)
                .build();
        return postResponse;
    }
}