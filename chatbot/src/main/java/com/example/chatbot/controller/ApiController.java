package com.example.chatbot.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.chatbot.model.User;
import com.example.chatbot.service.IUserService;

@RestController
@RequestMapping("/api")
public class ApiController {
	
	@Autowired
    IUserService userService;

	//중복 아이디 체크
    @PostMapping("/user/check/id")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> checkUsername(@RequestParam String userId) {
        User user = userService.getUserInfoById(userId);
        Map<String, Object> map = new HashMap<String, Object>();
        
        if(user == null)
			map.put("result", "success");
		else
			map.put("result", "failed");
		
		return new ResponseEntity<>(map, HttpStatus.OK);
    }
    
    //중복 이메일 체크
    @PostMapping("/user/check/email")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> checkEmail(@RequestParam String email) {
        User user = userService.getUserInfoByEmail(email);
        Map<String, Object> map = new HashMap<String, Object>();
        
        if(user == null)
			map.put("result", "success");
		else
			map.put("result", "failed");
		
		return new ResponseEntity<>(map, HttpStatus.OK);
    }
    
    //나를 제외한 중복 이메일체크
    @PostMapping("/user/check/otheremail")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> checkOtherEmail(@RequestParam String email, String userId) {
        int user = userService.getUserInfoByOtherEmail(email, userId);
        Map<String, Object> map = new HashMap<String, Object>();
        
        if(user < 1)
			map.put("result", "success");
		else
			map.put("result", "failed");
		
		return new ResponseEntity<>(map, HttpStatus.OK);
    }
}
