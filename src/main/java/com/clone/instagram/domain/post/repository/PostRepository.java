package com.clone.instagram.domain.post.repository;

import com.clone.instagram.domain.post.model.Posts;
import com.clone.instagram.domain.user.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Posts, Long> {
    Posts findByWriter(Users user);
}
