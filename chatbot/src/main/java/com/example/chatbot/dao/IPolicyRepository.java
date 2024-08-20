package com.example.chatbot.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.chatbot.model.Policy;

public interface IPolicyRepository {

	List<Policy> getPoliciesByUserInfo(String userId, String age, String interest, String area, String employment, String academic);

	Policy getPolicyByPolicyId(int policyId);

	HashMap<String, Integer> getPolicyFieldsStatistics();
//	List<Policy> searchPolicy1(String employment, String academicAbility, List<String> selectedPolicies, List<String> selectedRegions);
//	List<Policy> searchPolicy2(String employment, String academicAbility, List<String> selectedPolicies, List<String> selectedRegions, String age);
//	List<Policy> searchPolicy3(String employment, String academicAbility, List<String> selectedPolicies, List<String> selectedRegions, String searchInput);
//	List<Policy> searchPolicy4(String employment, String academicAbility, List<String> selectedPolicies, List<String> selectedRegions, String age, String searchInput);

	Page<Policy> searchPolicy1(String employment, String academicAbility, List<String> selectedPolicies,
			List<String> selectedRegions, Pageable pageable);

	Page<Policy> searchPolicy2(String employment, String academicAbility, List<String> selectedPolicies,
			List<String> selectedRegions, String age, Pageable pageable);

	Page<Policy> searchPolicy3(String employment, String academicAbility, List<String> selectedPolicies,
			List<String> selectedRegions, String searchInput, Pageable pageable);

	Page<Policy> searchPolicy4(String employment, String academicAbility, List<String> selectedPolicies,
			List<String> selectedRegions, String age, String searchInput, Pageable pageable);

	List<Map<String, Object>> getPolicyMonthlyStatistics();
}
