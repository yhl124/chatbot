package com.example.chatbot.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.chatbot.model.Chatroom;

@Repository
public class ChatroomRepository implements IChatroomRepository {
	
	@Autowired
	JdbcTemplate jdbctemplate;

	@Override
	public void insertChatroom(Chatroom chatroom) {
		// TODO Auto-generated method stub
		
	}

}
