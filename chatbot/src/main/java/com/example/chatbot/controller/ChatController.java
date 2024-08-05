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
            List<Chatroom> chatrooms = chatroomService.getChatroomByUserId(user.getUserId());
            if (chatrooms != null) {
                model.addAttribute("chatRooms", chatrooms);
            } else {
                model.addAttribute("chatRooms", List.of()); // 빈 리스트 추가
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
	
	@GetMapping("/chat/{roomId}")
	public String getChatroom(@AuthenticationPrincipal User user, @PathVariable int roomId, Model model) {
		List<Chatroom> chatrooms = chatroomService.getChatroomByUserId(user.getUserId());
		Chatroom chatroom = chatroomService.getChatroomByRoomId(user.getUserId(), roomId);
		
		model.addAttribute("user", user);
		if (chatroom != null) {
            model.addAttribute("chatRooms", chatrooms);
        } else {
            model.addAttribute("chatRooms", List.of()); // 빈 리스트 추가 대신 에러 전달로 변경 필요
        }
		model.addAttribute("chatRoom", chatroom);
		
		return "chat";
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
