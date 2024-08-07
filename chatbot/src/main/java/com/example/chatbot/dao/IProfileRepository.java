package com.example.chatbot.dao;
import com.example.chatbot.model.Profile;


public interface IProfileRepository {
	public void uploadFile(Profile profile);
	public Profile getProfile(int profileId);
	public Profile getProfileByUserId(String userId);
	void deleteFileByUserId(String userId);
}
