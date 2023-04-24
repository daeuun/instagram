package com.clone.instagram.domain.message.api;

import com.clone.instagram.domain.message.model.ChatRoom;
import com.clone.instagram.domain.message.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1/chat")
public class ChatRoomController {
    private final ChatService chatService;

    @PostMapping("/room")
    @ResponseBody
    public ChatRoom createRoom(@RequestParam String name) {
        return chatService.createRoom();
    }

    @GetMapping("/rooms")
    @ResponseBody
    public List<ChatRoom> rooms() {
        return chatService.rooms();
    }

    @GetMapping("/room/{roomId}")
    @ResponseBody
    public ChatRoom roomInfo(@PathVariable UUID roomId) {
        return chatService.room(roomId);
    }
}
