package com.clone.instagram.domain.message.repository;

import com.clone.instagram.domain.message.model.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, UUID> {
}
