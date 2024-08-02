package com.example.chatbot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.chatbot.dao.IChatroomRepository;
import com.example.chatbot.model.Chatroom;

@Service
public class ChatroomService implements IChatroomService {
	
	@Autowired
	IChatroomRepository chatroomRepository;

	@Override
	public void insertChatroom(Chatroom chatroom) {
		chatroomRepository.insertChatroom(chatroom);
	}
}
