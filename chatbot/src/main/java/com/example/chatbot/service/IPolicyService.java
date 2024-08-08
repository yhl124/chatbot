package com.example.chatbot.service;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;

import com.example.chatbot.model.Policy;

public interface IPolicyService {

	List<Policy> getPoliciesByUserInfo(String userId, Date birthday);

	Policy getPolicyByPolicyId(int policyId);
	HashMap<String, Integer> getPolicyFieldsStatistics();

}
