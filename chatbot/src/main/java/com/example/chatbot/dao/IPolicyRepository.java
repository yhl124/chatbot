package com.example.chatbot.dao;

import java.util.HashMap;
import java.util.List;

import com.example.chatbot.model.Policy;

public interface IPolicyRepository {

	List<Policy> getPoliciesByUserInfo(String userId, String age);

	Policy getPolicyByPolicyId(int policyId);

	HashMap<String, Integer> getPolicyFieldsStatistics();
}
