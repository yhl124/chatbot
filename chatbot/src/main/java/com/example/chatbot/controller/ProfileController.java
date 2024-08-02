package com.example.chatbot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.chatbot.model.Profile;
import com.example.chatbot.service.IProfileService;

import lombok.extern.slf4j.Slf4j;


@Controller
public class ProfileController {
	@Autowired
	IProfileService profileService;
	
	@GetMapping("/profile/file/{userId}")
	public ResponseEntity<byte[]> getProfileImage(@PathVariable String userId) {
		Profile profile = profileService.getProfileByUserId(userId);

		final HttpHeaders headers = new HttpHeaders();
		if(profile != null) {
			String[] mtypes = profile.getFileContentType().split("/");
			headers.setContentType(new MediaType(mtypes[0], mtypes[1]));
			headers.setContentLength(profile.getFileSize());
			headers.setContentDispositionFormData("attachment", profile.getFileName());
			return new ResponseEntity<byte[]>(profile.getFileData(), headers, HttpStatus.OK);
		} else {
			return new ResponseEntity<byte[]>(HttpStatus.NOT_FOUND);
		}
	}
}
