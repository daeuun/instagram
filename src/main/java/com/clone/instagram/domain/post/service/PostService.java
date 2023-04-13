package com.clone.instagram.domain.post.service;

import com.clone.instagram.domain.post.dto.CreatePostRequest;
import com.clone.instagram.domain.post.dto.PostDto;
import com.clone.instagram.domain.post.dto.PostResponse;
import com.clone.instagram.domain.post.dto.PostSearchCondition;
import com.clone.instagram.domain.post.model.PostImage;
import com.clone.instagram.domain.post.model.Posts;
import com.clone.instagram.domain.post.repository.PostImageRepository;
import com.clone.instagram.domain.post.repository.PostRepository;
import com.clone.instagram.domain.post.repository.PostRepositoryCustom;
import com.clone.instagram.domain.user.model.Users;
import com.clone.instagram.domain.user.repository.UserRepository;
import com.clone.instagram.exception.BusinessException;
import com.clone.instagram.exception.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class PostService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private PostRepositoryCustom postRepositoryCustom;
    @Autowired
    private PostImageRepository postImageRepository;

    @Transactional
    public Boolean create(CreatePostRequest request) {
        Users writer = userRepository.findByIdAndDeleted(request.getUserId(), false);
        postRepository.save(request.toEntity(writer));
        return true;
    }

    @Transactional
    public Posts update(PostDto postDto) {
        Posts updatedPost = postRepository.findById(postDto.getPostId())
                .map(post -> {
                    List<PostImage> updatedImages = postDto.getImages().stream()
                            .map(imageUrl -> new PostImage(imageUrl))
                            .collect(Collectors.toList());
                    post.updateContent(postDto.getContent());
                    post.updateImages(updatedImages);
                    return postRepository.save(post);
                })
                .orElseThrow(() -> new BusinessException(ErrorCode.POST_DOES_NOT_EXISTS));
        return updatedPost;
    }

    public List<PostResponse> getPosts(Long userId, PostSearchCondition condition) {
        Users user = userRepository.findByIdAndDeleted(userId, false);
        if (user == null) {
            throw new BusinessException(ErrorCode.USER_DOES_NOT_EXISTS);
        }
        String sortBy = (condition.getSortBy() != null) ? condition.getSortBy() : "defaultSortColumn";
        String sortOrder = (condition.getSortOrder() != null) ? condition.getSortOrder() : "DESC";
        Sort sort = Sort.by(Sort.Direction.fromString(sortOrder), condition.getSortBy() != null ? condition.getSortBy() : sortBy);

        List<PostResponse> searchPosts = postRepositoryCustom.findPostsByCondition(condition, user);
        return searchPosts;
    }

    @Transactional
    public Boolean delete(Long postId) {
        Users user = userRepository.findByEmailAndDeleted(contextId(), false);
        if (user == null) {
            throw new BusinessException(ErrorCode.USER_DOES_NOT_EXISTS);
        }
        List<PostImage> postImages = postImageRepository.findByPostId(postId);
        if (postImages.isEmpty()) {
            throw new BusinessException(ErrorCode.POST_IMAGE_DOES_NOT_EXISTS);
        }
        postImageRepository.deleteAll(postImages);
        postRepository.findById(postId)
                .ifPresentOrElse(postRepository::delete, () -> {
                    throw new BusinessException(ErrorCode.POST_DOES_NOT_EXISTS);
                });
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
