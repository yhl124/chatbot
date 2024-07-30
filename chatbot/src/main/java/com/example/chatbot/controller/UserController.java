package com.example.chatbot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.chatbot.model.User;
import com.example.chatbot.service.IUserService;

@Controller
public class UserController {

	@Autowired
	IUserService userService;


	@GetMapping({"/", "/main"}) 
	public String mainpage() 
	{ 
//		int user_cnt = userService.getUserCount();
//		System.out.println(user_cnt);
		return "main"; 
	}

	@GetMapping("/login")
	public String login() {
		return "login_form";
	}
}
