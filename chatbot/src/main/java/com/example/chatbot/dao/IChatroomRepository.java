package com.example.chatbot.dao;

import java.util.List;

import com.example.chatbot.model.Chatroom;
import com.example.chatbot.model.User;

public interface IChatroomRepository {

	void insertChatroom(String userId);
	Chatroom getLastChatroomForUserId(String userId);
	void deleteChatroomByRoomId(String userId, int roomId);
	List<Chatroom> getChatroomByUserId(String userId);
	void renameChatroomByRoomId(String roomName, String userId, int roomId);
	Chatroom getChatroomByRoomId(String userId, int roomId);
}
