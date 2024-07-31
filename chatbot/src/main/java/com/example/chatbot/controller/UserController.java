package com.example.chatbot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    public String mainpage(@AuthenticationPrincipal User user, Model model) {
        if (user != null) {
            model.addAttribute("user", user);
        }
        //System.out.println(model);
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
}