package com.clone.instagram.domain.message.service;

import com.clone.instagram.domain.message.model.ChatRoom;
import com.clone.instagram.domain.message.repository.ChatRoomRepository;
import com.clone.instagram.domain.message.repository.ChatRoomRepositoryCustom;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatRoomRepositoryCustom chatRoomRepositoryCustom;

    public ChatRoom createRoom() {
        ChatRoom newChatRoom = new ChatRoom();
        chatRoomRepository.save(newChatRoom);
        return newChatRoom;
    }

    public List<ChatRoom> rooms() {
        return chatRoomRepositoryCustom.findByIdAndSentAt();
    }

    public ChatRoom room(UUID roomId) {
        return chatRoomRepository.findById(roomId).orElse(null);
    }

}
