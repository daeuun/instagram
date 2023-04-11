package com.clone.instagram.domain.comment.model;

import com.clone.instagram.domain.comment.dto.CommentDto;
import com.clone.instagram.domain.post.model.Posts;
import com.clone.instagram.domain.user.dto.UpdateProfileRequest;
import com.clone.instagram.domain.user.model.Users;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Entity
@AllArgsConstructor
@Getter
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;

    @ManyToOne
    private Users user;

    @ManyToOne
    private Posts post;

    @ManyToOne
    private Comment originalComment;

    public Comment(String content, Users user, Posts post, Comment originalComment) {
        this.content = content;
        this.user = user;
        this.post = post;
        this.originalComment = originalComment;
    }

    public Comment() {}

    public Comment update(CommentDto request, Posts post) {
        this.content = request.getContent();
        this.post = post;
        return this;
    }
}