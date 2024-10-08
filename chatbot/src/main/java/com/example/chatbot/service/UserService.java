package com.example.chatbot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.chatbot.dao.IUserRepository;
import com.example.chatbot.model.User;

@Service
public class UserService implements IUserService {

    @Autowired
    IUserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.loadUserByUsername(username);
    }

    @Override
    public int getUserCount() {
        return userRepository.getUserCount();
    }

    @Override
    public void insertUser(User user) {
        // 비밀번호를 BCrypt로 암호화
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
    	
        userRepository.insertUser(user);
    }

	@Override
	public User getUserInfoById(String userId) {
		return userRepository.getUserInfoById(userId);
	}
	
	@Override
	public User getUserInfoByEmail(String email) {
		return userRepository.getUserInfoByEmail(email);
	}

	@Override
	public Integer getUserInfoByOtherEmail(String email, String userId) {
		return userRepository.getUserInfoByOtherEmail(email, userId);
	}

	@Override
	public void updateUser(User user) {
		userRepository.updateUser(user);		
	}
}