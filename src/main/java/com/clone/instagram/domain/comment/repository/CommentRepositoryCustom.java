package com.clone.instagram.domain.comment.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import static com.clone.instagram.domain.comment.model.QComment.comment;

@Repository
public class CommentRepositoryCustom {
    @Autowired
    private JPAQueryFactory jpaQueryFactory;
    public void deleteReplies(Long commentId) {
        jpaQueryFactory.delete(comment)
                .where(comment.originalComment.id.eq(commentId))
                .execute();
    }

}
