package com.example.chatbot.service;

import java.sql.Date;
import java.util.List;

import com.example.chatbot.model.Chat;

public interface IChatService {
	List<Chat> getChatsByRoomId(int roomId);
	void insertChatLog(int roomId, String sendText, Date sendTime, String recvText, Date recvTime);
}
