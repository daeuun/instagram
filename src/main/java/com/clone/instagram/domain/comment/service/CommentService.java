package com.clone.instagram.domain.comment.service;

import com.clone.instagram.domain.comment.dto.CommentDto;
import com.clone.instagram.domain.comment.dto.CreateCommentRequest;
import com.clone.instagram.domain.comment.model.Comment;
import com.clone.instagram.domain.comment.repository.CommentRepository;
import com.clone.instagram.domain.post.model.Posts;
import com.clone.instagram.domain.post.repository.PostRepository;
import com.clone.instagram.domain.user.model.Users;
import com.clone.instagram.domain.user.repository.UserRepository;
import com.clone.instagram.exception.BusinessException;
import com.clone.instagram.exception.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CommentService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private CommentRepository commentRepository;

    @Transactional
    public Comment create(CreateCommentRequest request) {
        Users user = userRepository.findByIdAndDeleted(request.getUserId(), false);
        if (user == null) {
            throw new BusinessException(ErrorCode.USER_DOES_NOT_EXISTS);
        }
        Posts post = postRepository.findById(request.getPostId())
                .orElseThrow(() -> new BusinessException(ErrorCode.POST_DOES_NOT_EXISTS));
        Comment originalComment = null;
        if (request.getOriginalCommentId() != null) {
            originalComment = commentRepository.findById(request.getOriginalCommentId())
                    .orElseThrow(() -> new BusinessException(ErrorCode.ORIGINAL_COMMENT_DOES_NOT_EXISTS));
        }

        Comment newComment = request.toEntity(user, post, originalComment);
        commentRepository.save(newComment);
        return newComment;
    }

    public List<Comment> getComments(Long postId) {
        List<Comment> comments = commentRepository.findAllByPostId(postId);
        return comments;
    }

    @Transactional
    public Comment update(Long commentId, CommentDto request) {
        Users user = userRepository.findByEmailAndDeleted(contextId(), false);
        if (user == null) {
            throw new BusinessException(ErrorCode.USER_DOES_NOT_EXISTS);
        }
        Posts post = postRepository.findById(request.getPostId())
                .orElseThrow(() -> new BusinessException(ErrorCode.POST_DOES_NOT_EXISTS));
        Comment updatedComment = commentRepository.findById(commentId)
                .map(original -> {
                    Comment commentToUpdate = original.update(request, post);
                    return commentRepository.save(commentToUpdate);
                })
                .orElseThrow(() -> new BusinessException(ErrorCode.COMMENT_DOES_NOT_EXISTS));
        return updatedComment;
    }

    private String contextId() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        }
        return null;
    }
}