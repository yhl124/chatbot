package com.example.chatbot.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.chatbot.model.Policy;
import com.example.chatbot.model.User;
import com.example.chatbot.service.IPolicyService;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class PolicyController {
	
	@Autowired
	IPolicyService policyService;
	
	
    //추천페이지 이동
    @GetMapping("/recs")
    public String recspage(@AuthenticationPrincipal User user, Model model) {
    	
        if (user != null) {
            model.addAttribute("user", user);
            //유저 정보(id, 생년월일)로 맞는 정책 가져오기
            List<Policy> policies = policyService.getPoliciesByUserInfo(user.getUserId(), user.getBirthday());
            //분야별 정책 count값 가져오기
            HashMap<String, Integer> statistics = policyService.getPolicyFieldsStatistics();
            model.addAttribute("policyStatistics", statistics);
            if (policies != null) {
                model.addAttribute("policies", policies);
            } else {
                model.addAttribute("policies", List.of()); // 빈 리스트 추가
            }
        } 
        return "recommendation";
    }
    
    //검색페이지 이동
    @GetMapping("/search")
    public String searchpage(@AuthenticationPrincipal User user, Model model) {
        if (user != null) {
            model.addAttribute("user", user);
        } 
        return "search";
    }
    
    //검색 처리 지금 이거 있으면 스프링 에러남
//    @GetMapping("/search")
//    public String processSearch(
//            @RequestParam(name = "employment", required = false) String employment,
//            @RequestParam(name = "academicAbility", required = false) String academicAbility,
//            @RequestParam(name = "age", required = false) Integer age,
//            @RequestParam(name = "policies", required = false) List<String> policies,
//            @RequestParam(name = "region", required = false) String region,
//            Model model) {
//
//        // 각 파라미터가 null이 아닌 경우 처리
//        if (employment != null) {
//            model.addAttribute("employment", employment);
//            log.info("Employment: " + employment);
//        }
//
//        if (academicAbility != null) {
//            model.addAttribute("academicAbility", academicAbility);
//            log.info("Academic Ability: " + academicAbility);
//        }
//
//        if (age != null) {
//            model.addAttribute("age", age);
//            log.info("Age: " + age);
//        }
//
//        if (policies != null && !policies.isEmpty()) {
//            model.addAttribute("policies", policies);
//            log.info("Selected Policies: " + policies);
//        }
//
//        if (region != null) {
//            model.addAttribute("region", region);
//            log.info("Region: " + region);
//        }
//
//        // 검색 결과 처리 로직
//        // List<Result> searchResults = searchService.search(employment, academicAbility, age, policies, region);
//        // model.addAttribute("searchResults", searchResults);
//
//        // 검색 결과 처리 로직 작성 (예: 데이터베이스 조회 등)
//        // 예를 들어, 검색 결과를 model에 추가
//        // List<Result> searchResults = searchService.search(policies, regions);
//        // model.addAttribute("searchResults", searchResults);
//        
//        return "search"; 
//    }
   
    
    //상세페이지 이동
	@GetMapping("/recs/{policyId}")
	public String getChatroom(@AuthenticationPrincipal User user, @PathVariable int policyId, Model model, RedirectAttributes redirectAttributes) {
	    
	    Policy policy = policyService.getPolicyByPolicyId(policyId);

	    model.addAttribute("user", user);
	    model.addAttribute("policy", policy);

	    
	    if (policy == null) {
	        redirectAttributes.addFlashAttribute("errorMessage", "잘못된 접근입니다");
	        return "redirect:/recs";
	    }

	    return "detail";
	}
	
	
}
