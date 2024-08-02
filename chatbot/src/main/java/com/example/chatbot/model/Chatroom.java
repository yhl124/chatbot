package com.example.chatbot.model;

import java.sql.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Chatroom {
	private int roomId;
	private String userId;
	private String RoomName;
	private Date LastUse;
	private Date GenerationTime;
}
