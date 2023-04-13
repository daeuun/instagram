package com.clone.instagram.domain.post.repository;

import com.clone.instagram.domain.post.dto.PostCursor;
import com.clone.instagram.domain.post.dto.PostResponse;
import com.clone.instagram.domain.post.dto.PostSearchCondition;
import com.clone.instagram.domain.post.model.Posts;
import com.clone.instagram.domain.user.model.Users;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

import static com.clone.instagram.domain.post.model.QPosts.posts;
import static com.querydsl.jpa.JPAExpressions.selectFrom;

@Repository
@RequiredArgsConstructor
public class PostRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    public List<PostResponse> findPostsByCondition(PostSearchCondition condition, Users writer) {
        List<Posts> postsList = selectFrom(posts)
                .where(posts.createdAt.loe(condition.getCursor().getCreatedAt()),
                        posts.id.lt(condition.getCursor().getId()),
                        posts.writer.eq(writer))
                .orderBy(posts.createdAt.desc(), posts.id.desc())
                .limit(condition.getPageSize() + 1)
                .fetch();

        List<PostResponse> content = postsList.stream()
                .map(PostResponse::of)
                .collect(Collectors.toList());

        return content;
    }

}