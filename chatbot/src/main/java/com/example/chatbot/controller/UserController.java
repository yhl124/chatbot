package com.example.chatbot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.chatbot.service.IUserService;


@Controller
public class UserController {
	
	@Autowired
	IUserService userService;
	
	/*
	 * @GetMapping({"", "/main"}) public String mainpage() { return "main"; }
	 */
	
    @GetMapping("/main")
    public String home() {
        return "main";
    }
	
    @GetMapping("/login")
    public String target() {
        return "login";
    }
}
