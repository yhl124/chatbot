package com.example.chatbot.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.chatbot.dao.IUserRepository;
import com.example.chatbot.model.User;

public class UserService implements IUserService {
	
	@Autowired
	IUserRepository userRepository;

	@Override
	public User getUserInfo(String userId) {
		return userRepository.getUserInfo(userId);
	}

}
