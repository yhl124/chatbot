package com.example.chatbot.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.chatbot.model.Chatroom;
import com.example.chatbot.model.User;
import com.example.chatbot.service.IChatroomService;

@Controller
public class ChatController {
	
	@Autowired
	IChatroomService chatroomService;
	
	
	@GetMapping("/chat")
    public String chatPage(@AuthenticationPrincipal User user, Model model) {
        if (user != null) {
            model.addAttribute("user", user);
            // 사용자 ID로 채팅 목록을 가져오기
            List<Chatroom> chatroom = chatroomService.getChatroomByUserId(user.getUserId());
            if (chatroom != null) {
                model.addAttribute("chatroom", chatroom);
            } else {
                model.addAttribute("chatroom", List.of()); // 빈 리스트 추가
            }
        } 
        return "chat";
    }

	
	@PostMapping("/chat/create")
    public ResponseEntity<Chatroom> insertChatroom(@RequestBody User user) {
        chatroomService.insertChatroom(user);
        Chatroom newChatroom = chatroomService.getLastChatroomForUser(user);
        
        return ResponseEntity.ok(newChatroom);
    }
	
	@PostMapping("/chat/delete/{roomId}")
	public ResponseEntity<?> deleteChatroom(@PathVariable int roomId, @RequestBody Map<String, String> request) {
	    String userId = request.get("userId");
	    chatroomService.deleteChatroomByRoomId(userId, roomId);
	    return ResponseEntity.ok().build();
	}
	
	@PostMapping("/chat/rename/{roomId}")
    public ResponseEntity<?> renameChatroom(@PathVariable int roomId, @RequestBody Map<String, String> request) {
		String userId = request.get("userId");
        String roomName = request.get("roomName");
        chatroomService.renameChatroomByRoomId(roomName, userId, roomId);
        
        Map<String, String> response = new HashMap<>();
        response.put("roomName", roomName);
        
        return ResponseEntity.ok(response);
    }
	
	
}
