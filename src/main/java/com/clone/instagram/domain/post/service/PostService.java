package com.clone.instagram.domain.post.service;

import com.clone.instagram.domain.post.dto.CreatePostRequest;
import com.clone.instagram.domain.post.dto.PostDto;
import com.clone.instagram.domain.post.model.PostImage;
import com.clone.instagram.domain.post.model.Posts;
import com.clone.instagram.domain.post.repository.PostRepository;
import com.clone.instagram.domain.user.model.Users;
import com.clone.instagram.domain.user.repository.UserRepository;
import com.clone.instagram.exception.BusinessException;
import com.clone.instagram.exception.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class PostService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PostRepository postRepository;

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

}
