package com.example.chatbot.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.chatbot.model.User;

@Controller
public class ChatController {
	
	
	@GetMapping("/chat")
    public String chatPage(@AuthenticationPrincipal User user, Model model) {
        if (user != null) {
            model.addAttribute("user", user);
        } 
        return "chat";
    }
	
	@PostMapping("/chat/create")
	public void insertChatroom(User user) {
		
	}
}
