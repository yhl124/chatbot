package com.example.chatbot.service;

import com.example.chatbot.model.Profile;

public interface IProfileService {
	public void uploadFile(Profile profile);
	public Profile getProfile(int profileId);
	public Profile getProfileByUserId(String userId);
	void deleteFileByUserId(String userId);
}
