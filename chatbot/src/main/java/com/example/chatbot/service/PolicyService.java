package com.example.chatbot.service;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.time.LocalDate;
import java.time.Period;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.chatbot.dao.IPolicyRepository;
import com.example.chatbot.model.Policy;

@Service
public class PolicyService implements IPolicyService {
	
	@Autowired
	IPolicyRepository policyRepository;

	@Override
	public List<Policy> getPoliciesByUserInfo(String userId, Date birthday, String interest, String area, String employment, String academic) {
		//sql.date 타입을 localdate 타입의 생일, 오늘을 사용해서 만나이 계산
		LocalDate birthLocalDate = birthday.toLocalDate();
        LocalDate currentDate = LocalDate.now();
        
        Period period = Period.between(birthLocalDate, currentDate);
        int age = period.getYears();
		
		
		return policyRepository.getPoliciesByUserInfo(userId, Integer.toString(age), interest, area, employment, academic);
	}

	@Override
	public Policy getPolicyByPolicyId(int policyId) {
		return policyRepository.getPolicyByPolicyId(policyId);
	}

	@Override
	public HashMap<String, Integer> getPolicyFieldsStatistics() {
		return policyRepository.getPolicyFieldsStatistics();
	}

	/************
	@Override
	public List<Policy> searchPolicy1(String employment, String academicAbility, List<String> selectedPolicies,
			List<String> selectedRegions) {
		return policyRepository.searchPolicy1(employment, academicAbility, selectedPolicies, selectedRegions);
	}

	@Override
	public List<Policy> searchPolicy2(String employment, String academicAbility, List<String> selectedPolicies,
			List<String> selectedRegions, String age) {
		return policyRepository.searchPolicy2(employment, academicAbility, selectedPolicies, selectedRegions, age);
	}

	@Override
	public List<Policy> searchPolicy3(String employment, String academicAbility, List<String> selectedPolicies,
			List<String> selectedRegions, String searchInput) {
		return policyRepository.searchPolicy3(employment, academicAbility, selectedPolicies, selectedRegions, searchInput);
	}

	@Override
	public List<Policy> searchPolicy4(String employment, String academicAbility, List<String> selectedPolicies,
			List<String> selectedRegions, String age, String searchInput) {
		return policyRepository.searchPolicy4(employment, academicAbility, selectedPolicies, selectedRegions, age, searchInput);
	}
	***********************/
	//////////////////////////////////////
	@Override
	public Page<Policy> searchPolicy1(String employment, String academicAbility, List<String> selectedPolicies,
			List<String> selectedRegions, Pageable pageable) {
		return policyRepository.searchPolicy1(employment, academicAbility, selectedPolicies, selectedRegions, pageable);
	}

	@Override
	public Page<Policy> searchPolicy2(String employment, String academicAbility, List<String> selectedPolicies,
			List<String> selectedRegions, String age, Pageable pageable) {
		return policyRepository.searchPolicy2(employment, academicAbility, selectedPolicies, selectedRegions, age, pageable);
	}

	@Override
	public Page<Policy> searchPolicy3(String employment, String academicAbility, List<String> selectedPolicies,
			List<String> selectedRegions, String searchInput, Pageable pageable) {
		return policyRepository.searchPolicy3(employment, academicAbility, selectedPolicies, selectedRegions, searchInput, pageable);
	}

	@Override
	public Page<Policy> searchPolicy4(String employment, String academicAbility, List<String> selectedPolicies,
			List<String> selectedRegions, String age, String searchInput, Pageable pageable) {
		return policyRepository.searchPolicy4(employment, academicAbility, selectedPolicies, selectedRegions, age, searchInput, pageable);
	}

	@Override
	public List<Map<String, Object>> getPolicyMonthlyStatistics() {
		return policyRepository.getPolicyMonthlyStatistics();
	}

}
