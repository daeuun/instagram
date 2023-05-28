package com.clone.instagram.domain.comment.service;

import com.clone.instagram.domain.comment.dto.CommentDto;
import com.clone.instagram.domain.comment.dto.CommentCreateRequest;
import com.clone.instagram.domain.comment.models.Comment;
import com.clone.instagram.domain.comment.resources.CommentMapper;
import com.clone.instagram.domain.comment.resources.CommentRepository;
import com.clone.instagram.domain.comment.resources.CommentRepositoryCustom;
import com.clone.instagram.domain.post.model.Posts;
import com.clone.instagram.domain.post.repository.PostRepository;
import com.clone.instagram.domain.user.model.Users;
import com.clone.instagram.domain.user.repository.UserRepository;
import com.clone.instagram.exception.BusinessException;
import com.clone.instagram.exception.ErrorCode;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final CommentRepositoryCustom commentRepositoryCustom;
    private final CommentMapper commentMapper;

    @Transactional
    public void create(CommentCreateRequest request) {
        Users user = getUser(request);
        Posts post = getPost(request);
        Comment comment = commentMapper.from(request);
        Comment original = commentRepository.findById(request.getOriginalCommentId()).orElse(null);
        comment.create(user, post, original);
        commentRepository.save(comment);
    }

    private Users getUser(CommentCreateRequest request) {
        return userRepository.findByIdAndDeleted(request.getUserId(), false)
                .orElseThrow(() -> new EntityNotFoundException("user not found with id: $request.getUserId()"));
    }

    private Posts getPost(CommentCreateRequest request) {
        return postRepository.findById(request.getPostId())
                .orElseThrow(() -> new BusinessException(ErrorCode.POST_DOES_NOT_EXISTS));
    }

    public List<Comment> getComments(Long postId) {
        List<Comment> comments = commentRepository.findAllByPostId(postId);
        return comments;
    }

    @Transactional
    public Comment update(Long commentId, CommentDto request) {
        Users user = getUser();
        Posts post = getPost(request);
        Comment updatedComment = commentRepository.findById(commentId)
                .map(original -> {
                    Comment commentToUpdate = original.update(request, post);
                    return commentRepository.save(commentToUpdate);
                })
                .orElseThrow(() -> new BusinessException(ErrorCode.COMMENT_DOES_NOT_EXISTS));
        return updatedComment;
    }

    private Users getUser() {
        Users user = userRepository.findByEmailAndDeleted(contextId(), false);
        if (user == null) {
            throw new BusinessException(ErrorCode.USER_DOES_NOT_EXISTS);
        }
        return user;
    }

    private Posts getPost(CommentDto request) {
        Posts post = postRepository.findById(request.getPostId())
                .orElseThrow(() -> new BusinessException(ErrorCode.POST_DOES_NOT_EXISTS));
        return post;
    }

    @Transactional
    public Boolean hardDelete(Long commentId) {
        Comment targetComment = commentRepository.findById(commentId)
                .orElseThrow(() -> new BusinessException(ErrorCode.COMMENT_DOES_NOT_EXISTS));
        Posts post = postRepository.findById(targetComment.getPost().getId())
                .orElseThrow(() -> new BusinessException(ErrorCode.POST_DOES_NOT_EXISTS));
        try {
            commentRepositoryCustom.deleteReplies(commentId);
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.COMMENT_DOES_NOT_EXISTS);
        }
        commentRepository.delete(targetComment);
        return true;
    }

    @Transactional
    public Boolean delete(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new BusinessException(ErrorCode.COMMENT_DOES_NOT_EXISTS));
        comment.softDelete();
        List<Comment> comments = comment.findCommentsToDelete();
        comments.forEach(commentToDelete -> commentRepository.delete(commentToDelete));
        return true;
    }

    private String contextId() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        }
        return null;
    }
}