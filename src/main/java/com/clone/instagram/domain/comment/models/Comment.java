package com.clone.instagram.domain.comment.models;

import com.clone.instagram.domain.comment.dto.CommentCreateRequest;
import com.clone.instagram.domain.comment.dto.CommentDto;
import com.clone.instagram.domain.post.model.Posts;
import com.clone.instagram.domain.user.model.Users;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity
@AllArgsConstructor
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
@Getter
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 2000)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "writer_id")
    private Users writer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Posts post;

    @ManyToOne
    @JoinColumn(name = "original_comment_id")
    private Comment originalComment;

    @OneToMany(mappedBy = "originalComment")
    private List<Comment> replies = new ArrayList<>();

    private boolean deleted = false;

    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public Comment(String content, Users writer, Posts post) {
        this.content = content;
        this.writer = writer;
        this.post = post;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void create(Users writer, Posts post, Comment originalComment) {
        this.writer = writer;
        this.post = post;
        this.originalComment = originalComment;
    }

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
                    if(originalComment.isDeleted() &&
                            originalComment.isAllRepliesDeleted()) {
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