package com.clone.instagram.domain.message.service;

import com.clone.instagram.domain.message.model.ChatRoom;
import com.clone.instagram.domain.message.repository.ChatRoomRepository;
import com.clone.instagram.domain.message.repository.ChatRoomRepositoryCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ChatService {

    @Autowired
    private ChatRoomRepository chatRoomRepository;
    @Autowired
    private ChatRoomRepositoryCustom chatRoomRepositoryCustom;

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
