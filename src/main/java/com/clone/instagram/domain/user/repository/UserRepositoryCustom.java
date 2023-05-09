package com.clone.instagram.domain.user.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.clone.instagram.domain.user.model.QUserFollower.userFollower;
import static com.clone.instagram.domain.user.model.QUserFollowing.userFollowing;
import static com.querydsl.jpa.JPAExpressions.selectFrom;


@Repository
@RequiredArgsConstructor
public class UserRepositoryCustom {
    public Long findFollowerCount(Long userId) {
        return selectFrom(userFollower)
                .where(userFollower.user.id.eq(userId))
                .fetchCount();
    }

    public Long findFollowingCount(Long userId) {
        return selectFrom(userFollowing)
                .where(userFollowing.user.id.eq(userId))
                .fetchCount();
    }

//    public Long findFollowingCount(Long userId) {
//        return selectFrom(users.users)
//                .leftJoin(userFollower.userFollowing).fetchJoin()
//                .where(userFollowing.userFollowing.user.id.eq(userIdg))
//                .count();
//    }
}
