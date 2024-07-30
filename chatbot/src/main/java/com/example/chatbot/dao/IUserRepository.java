package com.example.chatbot.dao;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.example.chatbot.model.User;

public interface IUserRepository {
	UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

	int getUserCount();
}
