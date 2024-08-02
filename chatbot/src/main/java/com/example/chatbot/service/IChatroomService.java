package com.example.chatbot.service;

import java.util.List;

import com.example.chatbot.model.Chatroom;
import com.example.chatbot.model.User;

public interface IChatroomService {
	
	public void insertChatroom(User user);
	public Chatroom getLastChatroomForUser(User user);
	void deleteChatroomByRoomId(String userId, int roomId);
	List<Chatroom> getChatroomByUserId(String userId);
	void renameChatroomByRoomId(String roomName, String userId, int roomId);

}
