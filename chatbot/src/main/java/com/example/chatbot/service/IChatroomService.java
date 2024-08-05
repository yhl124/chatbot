package com.example.chatbot.service;

import java.util.List;

import com.example.chatbot.model.Chatroom;
import com.example.chatbot.model.User;

public interface IChatroomService {
	
	public void insertChatroom(String UserId);
	public Chatroom getLastChatroomForUserId(String userId);
	void deleteChatroomByRoomId(String userId, int roomId);
	List<Chatroom> getChatroomByUserId(String userId);
	void renameChatroomByRoomId(String roomName, String userId, int roomId);
	Chatroom getChatroomByRoomId(String userId, int roomId);
	public boolean isNumeric(String strNum);
}
