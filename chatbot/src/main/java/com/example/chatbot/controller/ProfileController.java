package com.example.chatbot.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

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
			try {
				headers.setContentDispositionFormData("attachment", URLEncoder.encode(profile.getFileName(), "UTF-8"));
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return new ResponseEntity<byte[]>(profile.getFileData(), headers, HttpStatus.OK);
		} else {
			return new ResponseEntity<byte[]>(HttpStatus.NOT_FOUND);
		}
	}
}
