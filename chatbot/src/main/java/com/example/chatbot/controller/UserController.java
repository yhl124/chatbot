package com.example.chatbot.controller;

import java.io.IOException;
import java.nio.file.Files;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
            log.info("New User ID: " + newUser.getUserId());

            Profile profile = new Profile();
            profile.setUserId(newUser.getUserId());

            if (file != null && !file.isEmpty()) {
                profile.setFileName(file.getOriginalFilename());
                profile.setFileSize(file.getSize());
                profile.setFileContentType(file.getContentType());
                profile.setFileData(file.getBytes());
            } else {
                // 기본 프로필 이미지를 설정하는 로직
                profile.setFileName("defaultprofile.jpg");
                profile.setFileContentType("image/jpeg");
                // 기본 프로필 이미지의 바이트 데이터를 읽어와서 설정
                try {
                    ClassPathResource resource = new ClassPathResource("static/images/defaultprofile.jpg");
                    byte[] defaultProfileImageData = Files.readAllBytes(resource.getFile().toPath());
                    profile.setFileData(defaultProfileImageData);
                    profile.setFileSize((long) defaultProfileImageData.length);
                } catch (IOException e) {
                    log.error("Failed to load default profile image", e);
                    throw new RuntimeException("Failed to load default profile image", e);
                }
            }

            profileService.uploadFile(profile);

            model.addFlashAttribute("message", "회원가입에 성공하였습니다.");
        } catch (Exception e) {
            log.error("Error during signup: " + e.getMessage(), e);
            model.addFlashAttribute("message", "회원가입에 실패하였습니다: " + e.getMessage());
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