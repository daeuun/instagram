package com.clone.instagram.domain.user.repository;

import com.clone.instagram.domain.user.model.UserFollowing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserFollowingRepository extends JpaRepository<UserFollowing, Long> {
    List<UserFollowing> findAllById(Long userId);

}
