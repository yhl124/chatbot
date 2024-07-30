package com.example.chatbot.service;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.chatbot.model.User;

public interface IUserService {
	int getUserCount();
	UserDetails loadUserByUsername(String username);
	void insertUser(User user);
}
