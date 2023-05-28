package com.clone.instagram.domain.comment.resources;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.clone.instagram.domain.comment.models.QComment.comment;

@Repository
@RequiredArgsConstructor
public class CommentRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    public void deleteReplies(Long commentId) {
        jpaQueryFactory.delete(comment)
                .where(comment.originalComment.id.eq(commentId))
                .execute();
    }

}
