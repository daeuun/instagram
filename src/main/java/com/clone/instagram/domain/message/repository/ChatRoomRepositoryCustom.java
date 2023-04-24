package com.clone.instagram.domain.message.repository;

import com.clone.instagram.domain.message.model.ChatRoom;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.clone.instagram.domain.message.model.QChatRoom.chatRoom;
import static com.clone.instagram.domain.message.model.QChatMessage.chatMessage;

@Repository
@RequiredArgsConstructor
public class ChatRoomRepositoryCustom{
    private final JPAQueryFactory queryFactory;

    public List<ChatRoom> findByIdAndSentAt() {
        return queryFactory
                .selectFrom(chatRoom)
                .leftJoin(chatRoom.messages, chatMessage).fetchJoin()
                .groupBy(chatRoom.id)
                .orderBy(chatMessage.sentAt.desc())
                .fetch();
    }
}
