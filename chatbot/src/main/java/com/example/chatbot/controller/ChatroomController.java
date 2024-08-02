package com.example.chatbot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.chatbot.model.Chatroom;
import com.example.chatbot.model.User;
import com.example.chatbot.service.IChatroomService;

@Controller
public class ChatroomController {
	
	@Autowired
	IChatroomService chatroomService;
	
	
	@GetMapping("/chat")
    public String chatPage(@AuthenticationPrincipal User user, Model model) {
        if (user != null) {
            model.addAttribute("user", user);
        } 
        return "chat";
    }
	
	@PostMapping("/chat/create")
    public ResponseEntity<Chatroom> insertChatroom(@RequestBody User user) {
        chatroomService.insertChatroom(user);
        Chatroom newChatroom = chatroomService.getLastChatroomForUser(user);
        
        return ResponseEntity.ok(newChatroom);
    }
}
