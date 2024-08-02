package com.example.chatbot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.chatbot.dao.IProfileRepository;
import com.example.chatbot.model.Profile;

@Service
public class ProfileService implements IProfileService {
	
	@Autowired
	IProfileRepository profileRepository;
	
	@Override
	public void uploadFile(Profile profile) {
		profileRepository.uploadFile(profile);		
	}

	@Override
	public void deleteFileByUserId(String userId) {
		profileRepository.deleteFileByUserId(userId);
	}

	@Override
	public Profile getProfile(int profileId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Profile getProfileByUserId(String userId) {
		return profileRepository.getProfileByUserId(userId);
	}
	
}
