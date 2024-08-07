package com.example.chatbot.service;

import java.sql.Date;
import java.util.List;
import java.time.LocalDate;
import java.time.Period;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.chatbot.dao.IPolicyRepository;
import com.example.chatbot.model.Policy;

@Service
public class PolicyService implements IPolicyService {
	
	@Autowired
	IPolicyRepository policyRepository;

	@Override
	public List<Policy> getPoliciesByUserInfo(String userId, Date birthday) {
		//sql.date 타입을 localdate 타입의 생일, 오늘을 사용해서 만나이 계산
		LocalDate birthLocalDate = birthday.toLocalDate();
        LocalDate currentDate = LocalDate.now();
        
        Period period = Period.between(birthLocalDate, currentDate);
        int age = period.getYears();
		
		
		return policyRepository.getPoliciesByUserInfo(userId, Integer.toString(age));
	}

	@Override
	public Policy getPolicyByPolicyId(int policyId) {
		return policyRepository.getPolicyByPolicyId(policyId);
	}

}
