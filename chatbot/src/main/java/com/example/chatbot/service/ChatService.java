package com.example.chatbot.service;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.chatbot.dao.IChatRepository;
import com.example.chatbot.model.Chat;

@Service
public class ChatService implements IChatService {
	
	@Autowired
	IChatRepository chatRepository;

	@Override
	public List<Chat> getChatsByRoomId(int roomId) {
		return chatRepository.getChatsByRoomId(roomId);
	}

	@Override
	public void insertChatLog(int roomId, String sendText, Date sendTime, String recvText, Date recvTime) {
		chatRepository.insertChatLog(roomId, sendText, sendTime, recvText, recvTime);	
	}

}
