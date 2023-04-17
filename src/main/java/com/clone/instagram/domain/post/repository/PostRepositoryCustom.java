package com.clone.instagram.domain.post.repository;

import com.clone.instagram.domain.post.dto.PostResponse;
import com.clone.instagram.domain.post.dto.PostSearchCondition;
import com.clone.instagram.domain.post.model.Posts;
import com.clone.instagram.domain.user.model.Users;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

import static com.clone.instagram.domain.post.model.QPosts.posts;

@Repository
@RequiredArgsConstructor
public class PostRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    public Long findOldestPostId() {
        return queryFactory.select(posts.id)
                .from(posts)
                .orderBy(posts.createdAt.asc())
                .limit(1)
                .fetchOne();
    }

    public List<PostResponse> findPostsByCondition(PostSearchCondition condition, Users writer) {
        List<Posts> postsList = queryFactory.selectFrom(posts)
                .where(posts.id.goe(condition.getCursor().getId()),
                        posts.writer.id.eq(writer.getId()))
                .orderBy(posts.id.desc())
                .limit(condition.getPageSize())
                .fetch();

        List<PostResponse> content = postsList.stream()
                .map(PostResponse::of)
                .collect(Collectors.toList());

        return content;
    }

}