package com.example.chatbot.controller;

import java.io.IOException;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.chatbot.model.Chat;
import com.example.chatbot.model.Chatroom;
import com.example.chatbot.model.User;
import com.example.chatbot.service.IChatService;
import com.example.chatbot.service.IChatroomService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class ChatController {
	
	@Autowired
	IChatroomService chatroomService;
	
	@Autowired
	IChatService chatService;
	
	
	@GetMapping("/chat")
    public String chatPage(@AuthenticationPrincipal User user, Model model) {
        if (user != null) {
            model.addAttribute("user", user);
            // 사용자 ID로 채팅 목록을 가져오기
            List<Chatroom> chatrooms = chatroomService.getChatroomByUserId(user.getUserId());
            if (chatrooms != null) {
                model.addAttribute("chatRooms", chatrooms);
            } else {
                model.addAttribute("chatRooms", List.of()); // 빈 리스트 추가
            }
        } 
        return "chat";
    }

	
	@PostMapping("/chat/create")
    public ResponseEntity<Chatroom> insertChatroom(@RequestBody String userId) {
        chatroomService.insertChatroom(userId);
        Chatroom newChatroom = chatroomService.getLastChatroomForUserId(userId);
        
        return ResponseEntity.ok(newChatroom);
    }
	
	@GetMapping("/chat/{roomId}")
	public String getChatroom(@AuthenticationPrincipal User user, @PathVariable int roomId, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) {
	    List<Chatroom> chatrooms = chatroomService.getChatroomByUserId(user.getUserId());
	    Chatroom chatroom = chatroomService.getChatroomByRoomId(user.getUserId(), roomId);
	    List<Chat> chats = chatService.getChatsByRoomId(roomId);

	    model.addAttribute("user", user);
	    model.addAttribute("chatRooms", chatrooms);
	    model.addAttribute("chats", chats != null ? chats : List.of());
	    model.addAttribute("chatRoom", chatroom);
	    
	    if (chatroom == null) {
	        redirectAttributes.addFlashAttribute("errorMessage", "잘못된 접근입니다");
	        return "redirect:/chat";
	    }

	    return "chat";
	}


	
	@PostMapping("/chat/delete/{roomId}")
	public ResponseEntity<?> deleteChatroom(@PathVariable int roomId, @RequestBody Map<String, String> request) {
	    String userId = request.get("userId");
	    chatroomService.deleteChatroomByRoomId(userId, roomId);
	    return ResponseEntity.ok().build();
	}
	
	@PostMapping("/chat/rename/{roomId}")
    public ResponseEntity<?> renameChatroom(@PathVariable int roomId, @RequestBody Map<String, String> request) {
		String userId = request.get("userId");
        String roomName = request.get("roomName");
        chatroomService.renameChatroomByRoomId(roomName, userId, roomId);
        
        Map<String, String> response = new HashMap<>();
        response.put("roomName", roomName);
        
        return ResponseEntity.ok(response);
    }
	

	
	@PostMapping("/chat/send")
    public ResponseEntity<?> sendMessageToChatbot(@RequestBody Map<String, String> request) {
        // 채팅창에서 입력받은 내용과 정보들을 각 변수에 저장
        String message = request.get("message");
        String userId = request.get("userId");

        // 챗봇에서 전달받을 내용을 저장하기 위한 변수들
        String recvText = null;
        Date recvTime = null;
        
        //각종 정보 표시를 위한 변수들
        Date sendTime = new java.sql.Date(System.currentTimeMillis());
        String roomId = request.get("roomId");
        String roomName = null;
        String isNewRoom = "false";
        
        //결과 전달용 변수 response
        Map<String, String> response = new HashMap<>();
        
        //새 채팅방에서 입력한 채팅이면
        if (!chatroomService.isNumeric(roomId)) {
        	chatroomService.insertChatroom(userId);
            Chatroom newChatroom = chatroomService.getLastChatroomForUserId(userId);
            roomId = Integer.toString(newChatroom.getRoomId());
            roomName = newChatroom.getRoomName();
            isNewRoom = "true";
        }

        // 챗봇 서버의 URL
        // String chatbotUrl = "https://b725b6dc45f0.ngrok.app/botresponse";
        String chatbotUrl = "http://localhost:5000/botresponse";
        try {
            // 챗봇 서버로 메시지 전달을 위한 준비물
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            // 챗봇에 보낼 JSON 만들기 (내용으로 message, userId 삽입)
            Map<String, String> chatbotRequest = new HashMap<>();
            chatbotRequest.put("message", message);
            chatbotRequest.put("roomId", roomId);
            chatbotRequest.put("userId", userId);
            // ???
            HttpEntity<Map<String, String>> entity = new HttpEntity<>(chatbotRequest, headers);

            // 챗봇에게 응답받은 것
            ResponseEntity<String> chatbotResponse = restTemplate.exchange(
                    chatbotUrl, HttpMethod.POST, entity, String.class);

            // JSON 응답 파싱 및 각 요소 출력
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                JsonNode root = objectMapper.readTree(chatbotResponse.getBody());
                if (root.has("bot_response")) {
                    recvText = root.get("bot_response").asText();
                }
                recvTime = new java.sql.Date(System.currentTimeMillis());
                //log.info("응답받음");

            } catch (IOException e) {
                log.error("Error parsing JSON response: " + e.getMessage());
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Error parsing JSON response");
            }

            // chatService.insertChatLog(roomId, 보낸텍스트, 보낸시간, 받은텍스트, 받은시간);
            chatService.insertChatLog(Integer.valueOf(roomId), message, sendTime, recvText, recvTime);
            
            response.put("isNewRoom", isNewRoom);
            response.put("userId", userId);
            response.put("bot_response", recvText);
            response.put("roomId", roomId);
            response.put("roomName", roomName);
            
            return ResponseEntity.ok(response);
            // 챗봇 서버로부터 받은 응답 반환
            //return ResponseEntity.ok(chatbotResponse.getBody());
        } catch (Exception e) {
            log.error("Error communicating with chatbot server: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error communicating with chatbot server");
        }
    }

	
}
