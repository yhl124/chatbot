package com.example.chatbot.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
            List<Policy> policies = policyService.getPoliciesByUserInfo(user.getUserId(), user.getBirthday(), user.getInterest(), user.getArea(), user.getEmployment(), user.getAcademicAbility());
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
    
//    //검색 처리 미완성
//    @GetMapping(value = "/search", params={"searchInput", "employment", "academicAbility", "age", "selectedPolicies", "selectedRegions"})
//    public String processSearch(
//    		@RequestParam(name = "searchInput", required = false) String searchInput,
//            @RequestParam(name = "employment", required = false) String employment,
//            @RequestParam(name = "academicAbility", required = false) String academicAbility,
//            @RequestParam(name = "age",  defaultValue = "제한없음", required = false) String age,
//            @RequestParam(name = "selectedPolicies", required = false) List<String> selectedPolicies,
//            @RequestParam(name = "selectedRegions", required = false) List<String> selectedRegions,
//            @AuthenticationPrincipal User user,
//            Model model) {
//    	
//    	if (user != null) {
//            model.addAttribute("user", user);
//        } 
//    	
//    	//6개 파라미터중 searchInput(검색어), age(만나이)는 null일 수 있고 나머지는 값이 있다
//    	//각 경우에 따른 처리 따로 만들어줌
//    	
//    	log.info(employment);
//    	log.info(academicAbility);
//    	log.info(selectedPolicies.toString());
//    	log.info(selectedRegions.toString());
//    	//System.out.println(academicAbility);
//
//    	//둘다 null
//        if (searchInput.strip().length() == 0 && age.equals("제한없음")) {
//        	List<Policy> policies = policyService.searchPolicy1(employment, academicAbility, selectedPolicies, selectedRegions);
//
//        	model.addAttribute("policies", policies);
//            log.info("1");
//        }
//        //검색어만 null
//        else if (searchInput.strip().length() == 0 && !age.equals("제한없음")) {
//        	List<Policy> policies = policyService.searchPolicy2(employment, academicAbility, selectedPolicies, selectedRegions, age);
//        	model.addAttribute("policies", policies);
//        	log.info("2");
//        }
//        //나이만 null
//        else if (searchInput.strip().length() != 0 && age.equals("제한없음")) {
//        	List<Policy> policies = policyService.searchPolicy3(employment, academicAbility, selectedPolicies, selectedRegions, searchInput);
//        	model.addAttribute("policies", policies);
//        	log.info("3");
//        }
//        //둘 다 null 아님
//        else if (searchInput.strip().length() != 0 && !age.equals("제한없음")) {
//        	List<Policy> policies = policyService.searchPolicy4(employment, academicAbility, selectedPolicies, selectedRegions, age, searchInput);
//        	model.addAttribute("policies", policies);
//        	log.info("4");
//        }
//        
//        model.addAttribute("searchInput", searchInput);
//        model.addAttribute("age", age);
//        model.addAttribute("employment", employment);
//        model.addAttribute("academicAbility", academicAbility);
//        model.addAttribute("selectedPolicies", selectedPolicies);
//        model.addAttribute("selectedRegions", selectedRegions);
//
//        //return ResponseEntity.badRequest().build();
//        //log.info("dd2");
//        return "search"; 
//    }


    @GetMapping(value = "/search", params={"searchInput", "employment", "academicAbility", "age", "selectedPolicies", "selectedRegions"})
    public String processSearch(
        @RequestParam(name = "searchInput", required = false) String searchInput,
        @RequestParam(name = "employment", required = false) String employment,
        @RequestParam(name = "academicAbility", required = false) String academicAbility,
        @RequestParam(name = "age", defaultValue = "제한없음", required = false) String age,
        @RequestParam(name = "selectedPolicies", required = false) List<String> selectedPolicies,
        @RequestParam(name = "selectedRegions", required = false) List<String> selectedRegions,
        @RequestParam(name = "page", defaultValue = "0") int page, // 페이지 번호 추가
        @RequestParam(name = "size", defaultValue = "20") int size, // 페이지 크기 추가
        @AuthenticationPrincipal User user,
        Model model) {

        if (user != null) {
            model.addAttribute("user", user);
        }

        Pageable pageable = PageRequest.of(page, size);
        Page<Policy> policies = Page.empty(); // 초기에는 빈 페이지로 설정

//        if (searchInput != null && !searchInput.strip().isEmpty() || !age.equals("제한없음")) {
//            if (searchInput.strip().isEmpty() && age.equals("제한없음")) {
//                policies = policyService.searchPolicy1(employment, academicAbility, selectedPolicies, selectedRegions, pageable);
//                log.info("11");
//            } else if (searchInput.strip().isEmpty()) {
//                policies = policyService.searchPolicy2(employment, academicAbility, selectedPolicies, selectedRegions, age, pageable);
//                log.info("22");
//            } else if (age.equals("제한없음")) {
//                policies = policyService.searchPolicy3(employment, academicAbility, selectedPolicies, selectedRegions, searchInput, pageable);
//                log.info("33");
//            } else {
//                policies = policyService.searchPolicy4(employment, academicAbility, selectedPolicies, selectedRegions, age, searchInput, pageable);
//                log.info("44");
//            }
//        }
        
    	//둘다 null
        if (searchInput.strip().length() == 0 && age.equals("제한없음")) {
        	policies = policyService.searchPolicy1(employment, academicAbility, selectedPolicies, selectedRegions, pageable);
        	log.info("11");
        }
		  //검색어만 null
        else if (searchInput.strip().length() == 0 && !age.equals("제한없음")) {
        	policies = policyService.searchPolicy2(employment, academicAbility, selectedPolicies, selectedRegions, age, pageable);
        	log.info("22");
        }
		  //나이만 null
        else if (searchInput.strip().length() != 0 && age.equals("제한없음")) {
        	policies = policyService.searchPolicy3(employment, academicAbility, selectedPolicies, selectedRegions, searchInput, pageable);
			log.info("33");
        }
		  //둘 다 null 아님
        else if (searchInput.strip().length() != 0 && !age.equals("제한없음")) {
        	policies = policyService.searchPolicy4(employment, academicAbility, selectedPolicies, selectedRegions, age, searchInput, pageable);
        	log.info("44");
        }
        
        System.out.println("t "+pageable);
        System.out.println("p "+policies.getTotalPages());
//        if (policies.hasContent()) {
//            List<Policy> policyList = policies.getContent(); // 리스트 가져오기
//            for (Policy policy : policyList) {
//                System.out.println("Policy Name: " + policy.getPName());
//                System.out.println("Policy Region: " + policy.getRegion());
//                System.out.println("Policy End Date: " + policy.getEDate());
//                // 다른 필요한 멤버 변수들도 출력할 수 있습니다.
//            }
//        } else {
//            System.out.println("No policies found.");
//        }

        model.addAttribute("policies", policies);
        model.addAttribute("searchInput", searchInput);
        model.addAttribute("age", age);
        model.addAttribute("employment", employment);
        model.addAttribute("academicAbility", academicAbility);
        model.addAttribute("selectedPolicies", selectedPolicies);
        model.addAttribute("selectedRegions", selectedRegions);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", policies.getTotalPages());

        return "search";
    }

 
    //검색페이지 이동
    @GetMapping("/search")
    public String searchpage(@AuthenticationPrincipal User user, Model model) {
        if (user != null) {
            model.addAttribute("user", user);
            
            //검색페이지의 선택 기본값 설정
            List<String> defaultSelectedPolicies = List.of("분야 전체");
            List<String> defaultSelectedRegions = List.of("서울시");
            
            Pageable pageable = PageRequest.of(0, 20); 
            Page<Policy> emptyPolicies = new PageImpl<>(List.of(), pageable, 0); // 빈 페이지 생성
            
            model.addAttribute("policies", emptyPolicies); // 빈 페이지를 모델에 추가
            //model.addAttribute("policies", List.of());
            model.addAttribute("searchInput", null);
            model.addAttribute("age", null);
            model.addAttribute("academicAbility", "제한없음");
            model.addAttribute("employment", "제한없음");
            model.addAttribute("selectedPolicies", defaultSelectedPolicies);
            model.addAttribute("selectedRegions", defaultSelectedRegions);
        } 
        //log.info("dd");
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
