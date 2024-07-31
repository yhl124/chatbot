package com.example.chatbot.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.chatbot.model.Profile;
import com.example.chatbot.model.User;
import com.example.chatbot.service.IProfileService;
import com.example.chatbot.service.IUserService;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class UserController {

    @Autowired
    IUserService userService;
    
    @Autowired
    IProfileService profileService;

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
    	//log.info("test");
        return "login_form";
    }

    //회원가입 페이지 이동
    @GetMapping("/signup")
    public String signup() {
        return "signup_form";
    }

    //회원가입 버튼
    @PostMapping("/signup")
    public String insertUser(User user, @RequestParam("file") MultipartFile file, RedirectAttributes model) {
    	log.info(file.getOriginalFilename());
    	try {
			userService.insertUser(user);
			User newUser = userService.getUserInfoById(user.getUserId());
			log.info(newUser.getUserId());
			
			if(file != null && !file.isEmpty()) {
				Profile profile = new Profile();
				profile.setUserId(newUser.getUserId());
				profile.setFileName(file.getOriginalFilename());
				profile.setFileSize(file.getSize());
				profile.setFileContentType(file.getContentType());
				profile.setFileData(file.getBytes());
				profileService.uploadFile(profile);
			}
			model.addFlashAttribute("message", "회원가입에 성공하였습니다.");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			model.addFlashAttribute("message", e.getMessage());
		}
        return "redirect:/login";
    }
    
//    //업로드 테스트 페이지 이동
//    @GetMapping("/upload")
//    public String upload_test() {
//        return "uploadtest";
//    }
//    
//    //업로드 테스트
//    @PostMapping("/upload")
//    public String checkUpload(User user, MultipartFile file, RedirectAttributes model) {
//    	log.info(file.getOriginalFilename());
//    	try {
//			if(file != null && !file.isEmpty()) {
//				Profile profile = new Profile();
//				profile.setUserId("test1");
//				profile.setFileName(file.getOriginalFilename());
//				profile.setFileSize(file.getSize());
//				profile.setFileContentType(file.getContentType());
//				profile.setFileData(file.getBytes());
//				profileService.uploadFile(profile);
//			}
//			model.addFlashAttribute("message", "회원가입에 성공하였습니다.");
//		} catch (Exception e) {
//			System.out.println(e.getMessage());
//			model.addFlashAttribute("message", e.getMessage());
//		}
//    	return "redirect:/login";
//    }  
}