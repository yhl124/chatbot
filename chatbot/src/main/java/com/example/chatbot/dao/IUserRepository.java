package com.example.chatbot.dao;

import com.example.chatbot.model.User;

public interface IUserRepository {
	User getUserInfo(String userId);
}
