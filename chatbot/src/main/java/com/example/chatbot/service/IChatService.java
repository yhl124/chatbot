package com.example.chatbot.service;

import java.util.List;

import com.example.chatbot.model.Chat;

public interface IChatService {
	List<Chat> getChatsByRoomId(int roomId);
}
