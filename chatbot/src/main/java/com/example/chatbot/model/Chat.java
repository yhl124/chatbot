package com.example.chatbot.model;

import java.sql.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Chat {
	private int chatId;
	private int roomId;
	private String sendText;
	private Date sendTime;
	private String recvText;
	private Date recvTime;
}
