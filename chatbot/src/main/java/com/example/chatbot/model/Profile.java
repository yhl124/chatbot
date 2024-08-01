package com.example.chatbot.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Profile {
	private int profileId;
	private String userId;
	private String fileName;
	private long fileSize;
	private String fileContentType;
	private byte[] fileData;
}
