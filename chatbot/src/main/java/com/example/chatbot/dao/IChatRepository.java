package com.example.chatbot.dao;

import java.sql.Date;
import java.util.List;

import com.example.chatbot.model.Chat;

public interface IChatRepository {
	List<Chat> getChatsByRoomId(int roomId);
	void insertChatLog(int roomId, String sendText, Date sendTime, String recvText, Date recvTime);
}
