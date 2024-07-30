package com.example.chatbot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.chatbot.model.User;
import com.example.chatbot.service.IUserService;

@Controller
public class UserController {

	@Autowired
	IUserService userService;

	//메인 페이지 이동
	@GetMapping({"/", "/main"}) 
	public String mainpage() 
	{ 
//		int user_cnt = userService.getUserCount();
//		System.out.println(user_cnt);
		return "main"; 
	}

	//로그인 페이지 이동
	@GetMapping("/login")
	public String login() {
		return "login_form";
	}
	//회원가입 페이지 이동
	@GetMapping("/signup")
	public String signup() {
		return "signup_form";
	}
	//회원가입 버튼
	@PostMapping("/signup")
    public String insertUser(User user) {
        userService.insertUser(user);
        return "redirect:/login";
    }
	

//    @PostMapping("/register")
//    public String registerUser(User user, Model model) {
//        if (userService.isUserExists(user.getUserId())) {
//            model.addAttribute("error", "User already exists");
//            return "register";
//        }
//        userService.registerUser(user);
//        return "redirect:/login";
//    }
}
