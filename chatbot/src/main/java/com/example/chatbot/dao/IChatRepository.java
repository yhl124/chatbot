package com.example.chatbot.dao;

import java.util.List;

import com.example.chatbot.model.Chat;

public interface IChatRepository {
	List<Chat> getChatsByRoomId(int roomId);

}
