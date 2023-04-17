package com.clone.instagram.domain.comment.model;

import com.clone.instagram.domain.comment.dto.CommentDto;
import com.clone.instagram.domain.post.model.Posts;
import com.clone.instagram.domain.user.model.Users;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@AllArgsConstructor
@Getter
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "writer_id")
    @JsonIgnore
    private Users writer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    @JsonIgnore
    private Posts post;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "original_comment_id")
    private Comment originalComment;

    @OneToMany(mappedBy = "originalComment")
    private List<Comment> replies = new ArrayList<>();

    private boolean deleted = false;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public Comment(String content, Users writer, Posts post, Comment originalComment) {
        this.content = content;
        this.writer = writer;
        this.post = post;
        this.originalComment = originalComment;
        this.createdAt = LocalDateTime.now();
    }

    public Comment() {}

    public Comment update(CommentDto request, Posts post) {
        this.content = request.getContent();
        this.post = post;
        return this;
    }

    public void softDelete() {
        this.deleted = true;
    }

    public boolean isDeleted() {
        return this.deleted;
    }

    public List<Comment> findCommentsToDelete() {
        List<Comment> commentsToDelete = new ArrayList<>();
        Optional.ofNullable(this.originalComment).ifPresentOrElse(
                originalComment -> {
                    if(originalComment.isDeleted() && originalComment.isAllRepliesDeleted()) {
                        commentsToDelete.add(this);
                    }
                },
                () -> {
                    if (isAllRepliesDeleted()) {
                        commentsToDelete.add(this);
                        commentsToDelete.addAll(this.getReplies());
                    }
                }
        );
        return commentsToDelete;
    }

    private boolean isAllRepliesDeleted() {
        return getReplies().stream()
                .map(Comment::isDeleted)
                .filter(deleted -> !deleted)
                .findAny()
                .orElse(true);
    }
}