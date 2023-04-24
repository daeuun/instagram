package com.clone.instagram.domain.user.repository;

import com.clone.instagram.domain.user.model.UserFollower;
import com.clone.instagram.domain.user.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserFollowerRepository extends JpaRepository<UserFollower, Long> {

    boolean existsByUserAndFollower(Users currentUser, Users userToFollow);

    List<UserFollower> findAllById(Long userId);

    Optional<UserFollower> findByUserAndFollower(Users currentUser, Users userToUnFollow);
}
