package com.clone.instagram.domain.post.service;

import com.clone.instagram.domain.post.dto.CreatePostRequest;
import com.clone.instagram.domain.post.repository.PostRepository;
import com.clone.instagram.domain.user.model.Users;
import com.clone.instagram.domain.user.repository.UserRepository;
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

}
