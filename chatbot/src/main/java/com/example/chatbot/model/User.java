package com.example.chatbot.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class User {
	private String userId;
	private String password;
	private String name;
	private String birthday;
	private String gender;
	private String email;
}
