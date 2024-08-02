package com.example.chatbot.dao;

import com.example.chatbot.model.Chatroom;
import com.example.chatbot.model.User;

public interface IChatroomRepository {

	void insertChatroom(User user);
	Chatroom getLastChatroomForUser(User user);

}
