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
}
