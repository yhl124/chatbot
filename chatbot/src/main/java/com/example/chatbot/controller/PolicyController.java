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
    
    //검색 처리 미완성
    @GetMapping(value = "/search", params={"searchInput", "employment", "academicAbility", "age", "selectedPolicies", "selectedRegions"})
    public String processSearch(
    		@RequestParam(name = "searchInput", required = false) String searchInput,
            @RequestParam(name = "employment", required = false) String employment,
            @RequestParam(name = "academicAbility", required = false) String academicAbility,
            @RequestParam(name = "age",  defaultValue = "제한없음", required = false) String age,
            @RequestParam(name = "selectedPolicies", required = false) List<String> selectedPolicies,
            @RequestParam(name = "selectedRegions", required = false) List<String> selectedRegions,
            @AuthenticationPrincipal User user,
            Model model) {
    	
    	if (user != null) {
            model.addAttribute("user", user);
        } 
    	
    	//6개 파라미터중 searchInput(검색어), age(만나이)는 null일 수 있고 나머지는 기본값이 있어서
    	//경우에 따른 처리 따로 만들어줌
    	
    	model.addAttribute("employment", employment);
    	model.addAttribute("academicAbility", academicAbility);
    	model.addAttribute("selectedPolicies", selectedPolicies);
    	model.addAttribute("selectedRegions", selectedRegions);
    	
    	//log.info(searchInput);

    	//둘다 null
        if (searchInput.strip().length() == 0 && age.equals("제한없음")) {
            //log.info("1");
        }
        //검색어만 null
        else if (searchInput.strip().length() == 0 && !age.equals("제한없음")) {
        	model.addAttribute("age", age);
        	//log.info("2");
        }
        //나이만 null
        else if (searchInput.strip().length() != 0 && age.equals("제한없음")) {
        	model.addAttribute("searchInput", searchInput);
        	//log.info("3");
        }
        //둘 다 null 아님
        else if (searchInput.strip().length() != 0 && !age.equals("제한없음")) {
        	model.addAttribute("age", age);
        	model.addAttribute("searchInput", searchInput);
        	//log.info("4");
        }

        // 검색 결과 처리 로직
        // List<Result> searchResults = searchService.search(employment, academicAbility, age, policies, region);
        // model.addAttribute("searchResults", searchResults);

        // 검색 결과 처리 로직 작성 (예: 데이터베이스 조회 등)
        // 예를 들어, 검색 결과를 model에 추가
        // List<Result> searchResults = searchService.search(policies, regions);
        // model.addAttribute("searchResults", searchResults);
        
        return "search"; 
    }
    
    
    //검색페이지 이동
    @GetMapping("/search")
    public String searchpage(@AuthenticationPrincipal User user, Model model) {
        if (user != null) {
            model.addAttribute("user", user);
        } 
        return "search";
    }
   
    
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
