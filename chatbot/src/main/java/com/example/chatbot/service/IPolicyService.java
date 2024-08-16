package com.example.chatbot.service;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.chatbot.model.Policy;

public interface IPolicyService {

	List<Policy> getPoliciesByUserInfo(String userId, Date birthday, String interest, String area, String employment, String academic);

	Policy getPolicyByPolicyId(int policyId);
	HashMap<String, Integer> getPolicyFieldsStatistics();
	List<Policy> searchPolicy1(String employment, String academicAbility, List<String> selectedPolicies, List<String> selectedRegions);

	List<Policy> searchPolicy2(String employment, String academicAbility, List<String> selectedPolicies,
			List<String> selectedRegions, String age);

	List<Policy> searchPolicy3(String employment, String academicAbility, List<String> selectedPolicies,
			List<String> selectedRegions, String searchInput);

	List<Policy> searchPolicy4(String employment, String academicAbility, List<String> selectedPolicies,
			List<String> selectedRegions, String age, String searchInput);
	
    Page<Policy> searchPolicy1(String employment, String academicAbility, List<String> selectedPolicies, List<String> selectedRegions, Pageable pageable);
    Page<Policy> searchPolicy2(String employment, String academicAbility, List<String> selectedPolicies, List<String> selectedRegions, String age, Pageable pageable);
    Page<Policy> searchPolicy3(String employment, String academicAbility, List<String> selectedPolicies, List<String> selectedRegions, String searchInput, Pageable pageable);
    Page<Policy> searchPolicy4(String employment, String academicAbility, List<String> selectedPolicies, List<String> selectedRegions, String age, String searchInput, Pageable pageable);

}
