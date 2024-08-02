package com.example.chatbot.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.chatbot.dao.IChatroomRepository;
import com.example.chatbot.model.Chatroom;
import com.example.chatbot.model.User;

@Service
public class ChatroomService implements IChatroomService {
	
	@Autowired
	IChatroomRepository chatroomRepository;

	@Override
	public void insertChatroom(User user) {
		chatroomRepository.insertChatroom(user);
	}

	@Override
	public Chatroom getLastChatroomForUser(User user) {
		return chatroomRepository.getLastChatroomForUser(user);
	}
	
	@Override
	public List<Chatroom> getChatroomByUserId(String userId){
		return chatroomRepository.getChatroomByUserId(userId);
	}
	
	@Override
	public void deleteChatroomByRoomId(String userId, int roomId) {
		chatroomRepository.deleteChatroomByRoomId(userId, roomId);
	}
	
	@Override
	public void renameChatroomByRoomId(String roomName, String userId, int roomId) {
		chatroomRepository.renameChatroomByRoomId(roomName, userId, roomId);
	}
}
