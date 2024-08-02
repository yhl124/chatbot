package com.example.chatbot.service;

import com.example.chatbot.model.Chatroom;
import com.example.chatbot.model.User;

public interface IChatroomService {
	
	public void insertChatroom(User user);
	public Chatroom getLastChatroomForUser(User user);

}
