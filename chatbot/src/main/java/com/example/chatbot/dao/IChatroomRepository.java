package com.example.chatbot.dao;

import java.util.List;

import com.example.chatbot.model.Chatroom;
import com.example.chatbot.model.User;

public interface IChatroomRepository {

	void insertChatroom(User user);
	Chatroom getLastChatroomForUser(User user);
	void deleteChatroomByRoomId(String userId, int roomId);
	List<Chatroom> getChatroomByUserId(String userId);
	void renameChatroomByRoomId(String roomName, String userId, int roomId);
	Chatroom getChatroomByRoomId(int roomId);
}
