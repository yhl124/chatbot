package com.example.chatbot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.chatbot.dao.IUserRepository;
import com.example.chatbot.model.User;

@Service
public class UserService implements IUserService {
	
	@Autowired
	IUserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.loadUserByUsername(username);
    }

	@Override
	public int getUserCount() {
		// TODO Auto-generated method stub
		return userRepository.getUserCount();
	}
	
	@Override
	public void insertUser(User user) {
		userRepository.insertUser(user);
	}
}
